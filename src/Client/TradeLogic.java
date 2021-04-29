package Client;

import Server.AssetDBSource;
import Server.TradeDBSource;
import Server.UnitDBSource;
import Server.UserDBSource;

public class TradeLogic {

    private static UnitDBSource udb;
    private static UserDBSource usdb;
    private static TradeDBSource tdb;
    private static AssetDBSource adb;

    private static Trades trade;
    private static User user;
    private static Units unit;
    private static Assets asset;
    private static int totalCost;


    public static void setTrade(Trades newTrade, UnitDBSource unitDBSource, UserDBSource userDBSource,
                                TradeDBSource tradeDBSource, AssetDBSource assetDBSource) throws TradesException {
        trade = newTrade;
        user = usdb.getUser(trade.getUserName());
        unit = udb.getUnit(user.getUnit());
        asset = adb.getAsset(trade.getAssetId());
        totalCost = trade.getQuantity() * trade.getPrice();

        if (trade.getType() == Trades.TradeType.buy) {
            buyListing();
        } else {
            sellListing();
        }
        matchTrades(trade.getType());
    }

    private static void buyListing() throws TradesException {
        int credits = unit.getCredits();

        if (credits < totalCost) {
            throw new TradesException("Not enough credits");
        }
        else {
            tdb.addTrade(trade);
            unit.setCredits((credits - totalCost));
            udb.update(unit);
        }
    }

    private static void sellListing() throws TradesException {
        int quantityOwned = asset.getQuantity();
        int quantitySelling = trade.getQuantity();

        if (quantityOwned < quantitySelling) {
            throw new TradesException("Not enough assets");
        }
        else {
            tdb.addTrade(trade);
            asset.setQuantity((quantityOwned - quantitySelling));
            adb.update(asset);
        }
    }

    private static void matchTrades(Trades.TradeType type) {
        int matchingTrade = 0;

        // If it's a buy, look for sells for that asset
        if (type == Trades.TradeType.buy) {
            do{
                matchingTrade = tdb.matchSell(trade.getAssetId(), trade.getPrice());

                if (matchingTrade > 0){
                    Trades match = tdb.getTrade(matchingTrade);
                    settleTrade(trade, match);
                }
            } while (matchingTrade > 0);

        }
        // If it's a sell, look for buys for that asset
        else if (type == Trades.TradeType.sell) {
            do {
                matchingTrade = tdb.matchBuy(trade.getAssetId(), trade.getPrice());

                if (matchingTrade > 0) {
                    Trades match = tdb.getTrade(matchingTrade);
                    settleTrade(match, trade);
                }
            } while (matchingTrade > 0);

        }
    }

    private static void settleTrade(Trades buy, Trades sell) {

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
            // Remove count from asset table
            asset.setQuantity(asset.getQuantity() - sellingQty);
            // Update database
            udb.update(buyerUnit);
            udb.update(sellerUnit);
            adb.update(asset);
            // Can delete both trades as they have cancelled each other out
            tdb.delete(buy.getId());
            tdb.delete(sell.getId());
            //TODO: ADD qty to assets_purchased
            //TODO: ADD to trade HX
        }
        // If buy qty is less than sell qty
        else if (qtyDiff < 0) {
            // Return difference in credits if sell price was less than ask price
            buyerUnit.setCredits(buyerUnit.getCredits() + (costDiff * askingQty));
            // Transfer credits to seller
            sellerUnit.setCredits(sellerUnit.getCredits() + sellingPrice * askingQty);
            // Remove count from asset table
            asset.setQuantity(asset.getQuantity() - askingQty);
            // Update otherTrade qty available
            sell.setQuantity(sellingQty - askingQty);
            // Update database
            udb.update(buyerUnit);
            udb.update(sellerUnit);
            adb.update(asset);
            tdb.update(sell);
            // Delete buy trade from database as its qty is now 0
            tdb.delete(buy.getId());
        }
        // If buy qty is greater than sell qty
        else if (qtyDiff > 0) {
            // Return difference in credits if sell price was less than ask price
            buyerUnit.setCredits(buyerUnit.getCredits() + (costDiff * sellingQty));
            // Transfer credits to seller
            sellerUnit.setCredits(sellerUnit.getCredits() + sellingPrice * sellingQty);
            // Remove count from asset table
            asset.setQuantity(asset.getQuantity() - sellingQty);
            // Update trade qty available
            buy.setQuantity(askingQty - sellingQty);
            // Update database
            udb.update(buyerUnit);
            udb.update(sellerUnit);
            adb.update(asset);
            tdb.update(buy);
            // Can delete sell trade as its qty is now 0
            tdb.delete(sell.getId());
        }
    }
}
