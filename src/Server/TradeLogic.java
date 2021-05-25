package Server;

import Client.*;
import Client.Trades.TradeType;

/**
 * This class deals with listing, matching and settling trades
 */
public class TradeLogic {

    private UnitDBSource udb;
    private UserDBSource usdb;
    private TradeDBSource tdb;
    private AssetDBSource adb;
    private PurchasesDBSource pdb;
    private HistoryDBSource hdb;

    private Trades trade;
    private User user;
    private Units unit;
    private Assets asset;
    private int totalCost;

    /**
     * Constructor
     *
     * @param udb
     * @param usdb
     * @param tdb
     * @param adb
     * @param pdb
     * @param hdb
     */
    public TradeLogic(UnitDBSource udb, UserDBSource usdb, TradeDBSource tdb, AssetDBSource adb,
                      PurchasesDBSource pdb, HistoryDBSource hdb) {
        this.udb = udb;
        this.usdb = usdb;
        this.tdb = tdb;
        this.adb = adb;
        this.pdb = pdb;
        this.hdb = hdb;
    }

    /**
     * Sets a trade and calls private methods to deal with matching the trade to other trades
     *
     * @param newTrade The trade to be added to the database
     * @throws TradesException
     */
    public int setTrade(Trades newTrade) {
        trade = newTrade;
        trade.setId(generateTradeID());
        user = usdb.getUser(trade.getUserName());
        unit = udb.getUnit(user.getUnit());
        asset = adb.getAsset(trade.getAssetId());
        totalCost = trade.getQuantity() * trade.getPrice();
        try {
            if (trade.getType() == Trades.TradeType.buy) {
                buyListing();
            } else {
                sellListing();
            }
            matchTrades(trade.getType());
        } catch (TradesException te) {
            return -1;
        }
        return 0;
    }

    /**
     * Cancels a trade and updates the credit balance and asset quantity accordingly
     *
     * @param cancelledTrade The trade to be cancelled
     */
    public void cancelTrade(Trades cancelledTrade) {
        trade = cancelledTrade;
        user = usdb.getUser(trade.getUserName());
        unit = udb.getUnit(user.getUnit());
        asset = adb.getAsset(trade.getAssetId());
        totalCost = trade.getQuantity() * trade.getPrice();

        TradeHistory tradeToCancel = new TradeHistory(cancelledTrade);
        hdb.addToHistory(tradeToCancel);

        if (cancelledTrade.getType() == TradeType.buy) {
            int credits = unit.getCredits();
            unit.setCredits((credits + totalCost));
            udb.update(unit);
        } else {
            int qty = trade.getQuantity();
            int oldQty = asset.getQuantity();
            asset.setQuantity((qty + oldQty));
            adb.update(asset);
        }

        tdb.delete(trade.getId());
    }

    /**
     * Lists a buy order. Does some validity checks and removes credits from the lister's allowance
     *
     * @throws TradesException If the unit the user belongs to does not have enough credits
     */
    private void buyListing() throws TradesException {
        int credits = unit.getCredits();

        if (credits < totalCost) {
            throw new TradesException("Not enough credits");
        }
        else {
            // Add the trade to the database
            tdb.addTrade(trade);
            // Update the unit's credits
            unit.setCredits((credits - totalCost));
            // Update the credits entry in the database
            udb.update(unit);
        }
    }

    /**
     * Lists a sell order. Does some validity checks and removes the asset from the lister's record.
     *
     * @throws TradesException If the unit the user belongs to does not have enough of the asset it is selling.
     */
    private void sellListing() throws TradesException {
        int quantityOwned = asset.getQuantity();
        int quantitySelling = trade.getQuantity();

        if (quantityOwned < quantitySelling) {
            throw new TradesException("Not enough assets");
        }
        else {
            tdb.addTrade(trade);
            asset.setQuantity((quantityOwned - quantitySelling));
            // Update the asset's quantity in the database
            adb.update(asset);
        }
    }

    /**
     * Calls database methods to match trades based on the trade type (buy or sell) of the newly added trade
     *
     * @param type The type of trade (TradeType.buy or TradeType.sell)
     */
    private void matchTrades(Trades.TradeType type) {
        int matchingTrade = 0;

        // If it's a buy, look for sells for that asset
        if (type == Trades.TradeType.buy) {
            // Keep looking as long as there are matching trades
            // This should only be the case if there are sell orders that are too small to meet the full quantity
            do{
                matchingTrade = tdb.matchSell(trade.getAssetId(), trade.getPrice());

                if (matchingTrade > 0){
                    Trades match = tdb.getTrade(matchingTrade);
                    settleTrade(trade, match);
                }
            } while (matchingTrade > 0 && trade.getQuantity() != 0);

        }
        // If it's a sell, look for buys for that asset
        else if (type == Trades.TradeType.sell) {
            // Keep looking as long as there are matching trades
            // This should only be the case if there are buy order that are too small to meet the full quantity
            do {
                matchingTrade = tdb.matchBuy(trade.getAssetId(), trade.getPrice());

                if (matchingTrade > 0) {
                    Trades match = tdb.getTrade(matchingTrade);
                    settleTrade(match, trade);
                }
            } while (matchingTrade > 0 && trade.getQuantity() != 0);

        }
    }

    /**
     * Settles a trade by transferring credits to the seller and the asset to the buyer. Also checks for differences
     * between buy and sell price as well as buy and sell quantity.
     *
     * @param buy The buy trade
     * @param sell The sell trade
     */
    private void settleTrade(Trades buy, Trades sell) {

        TradeHistory newTrade = null;
        boolean tradeComplete;

        // Get the organisational unit each trade belongs to (based on user)
        Units buyerUnit = udb.getUnit(usdb.getUser(buy.getUserName()).getUnit());
        Units sellerUnit = udb.getUnit(usdb.getUser(sell.getUserName()).getUnit());

        int askingQty = buy.getQuantity();
        int sellingQty = sell.getQuantity();
        int askingPrice = buy.getPrice();
        int sellingPrice = sell.getPrice();

        // Check how many units of the asset the otherTrade is selling compared to buy order
        int qtyDiff = askingQty - sellingQty;
        int costDiff = askingPrice - sellingPrice;

        // If buy qty matches sell qty
        if (qtyDiff == 0) {
            // Return difference in credits if sell price was less than ask price
            buyerUnit.setCredits(buyerUnit.getCredits() + (costDiff * askingQty));
            // Transfer credits to seller
            sellerUnit.setCredits(sellerUnit.getCredits() + sellingPrice * sellingQty);
            // Update database
            udb.update(buyerUnit);
            udb.update(sellerUnit);
            // Can delete both trades as they have cancelled each other out
            tdb.delete(buy.getId());
            tdb.delete(sell.getId());
            // Add to asset_purchases table
            pdb.addToPurchases(buy.getAssetId(), buyerUnit.getUnitID(), buy.getQuantity());
            // Add to trade history
            newTrade = new TradeHistory(TradeType.complete, buy.getAssetId(), buy.getQuantity(), sellingPrice,
                    buy.getUserName(), sell.getUserName());
        }
        // If buy qty is less than sell qty
        else if (qtyDiff < 0) {
            // Return difference in credits if sell price was less than ask price
            buyerUnit.setCredits(buyerUnit.getCredits() + (costDiff * askingQty));
            // Transfer credits to seller
            sellerUnit.setCredits(sellerUnit.getCredits() + sellingPrice * askingQty);
            // Update otherTrade qty available
            sell.setQuantity(sellingQty - askingQty);
            // Update database
            udb.update(buyerUnit);
            udb.update(sellerUnit);
            tdb.update(sell);
            // Delete buy trade from database as its qty is now 0
            tdb.delete(buy.getId());
            // Add to asset_purchases table
            pdb.addToPurchases(buy.getAssetId(), buyerUnit.getUnitID(), buy.getQuantity());
            // Add to trade history
            newTrade = new TradeHistory(TradeType.complete, buy.getAssetId(), buy.getQuantity(), sellingPrice,
                    buy.getUserName(), sell.getUserName());
            buy.setQuantity(0);
        }
        // If buy qty is greater than sell qty
        else if (qtyDiff > 0) {
            // Return difference in credits if sell price was less than ask price
            buyerUnit.setCredits(buyerUnit.getCredits() + (costDiff * sellingQty));
            // Transfer credits to seller
            sellerUnit.setCredits(sellerUnit.getCredits() + sellingPrice * sellingQty);
            // Update trade qty available
            buy.setQuantity(askingQty - sellingQty);
            // Update database
            udb.update(buyerUnit);
            udb.update(sellerUnit);
            tdb.update(buy);
            // Can delete sell trade as its qty is now 0
            tdb.delete(sell.getId());
            // Add to asset_purchases table
            pdb.addToPurchases(buy.getAssetId(), buyerUnit.getUnitID(), sell.getQuantity());
            // Add to trade history
            newTrade = new TradeHistory(TradeType.complete, buy.getAssetId(), sell.getQuantity(), sellingPrice,
                    buy.getUserName(), sell.getUserName());
            sell.setQuantity(0);
        }
        hdb.addToHistory(newTrade);
    }

    /**
     * Gets the trade ID for a listing
     *
     * @return Trade ID to be assigned to the listing
     */
    private int generateTradeID() {
//        long time = System.currentTimeMillis();
//        String timeString = Long.toString(time);
//        int total = 0;
//        for (char c : timeString.toCharArray()) {
//            total += c - '0';
//        }
//        total += trade.getAssetId();
//        total+= trade.getPrice();
//        return total;
        return tdb.getTradeID();
    }
}
