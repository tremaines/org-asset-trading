package Client;

import Client.Trades.TradeType;

/**
 * Trade History gives the information about the asset and its trade
 * value along with the users that have traded it for users to know if they should
 * buy that asset.
 */
public class TradeHistory {

    int id;
    TradeType status;
    int asset;
    int qty;
    int credits;
    String buyer;
    String seller;

    public TradeHistory() {
    }

    public TradeHistory(Trades trade) {
        status = TradeType.cancelled;
        this.asset = trade.getAssetId();
        this.qty = trade.getQuantity();
        this.credits = trade.getPrice();
        if (trade.getType() == TradeType.buy) {
            this.buyer = trade.getUserName();
        } else {
            this.seller = trade.getUserName();
        }

    }

    public TradeHistory(TradeType status, int asset, int qty, int credits, String buyer, String seller) {
        this.status = status;
        this.asset = asset;
        this.qty = qty;
        this.credits = credits;
        this.buyer = buyer;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TradeType getStatus() {
        return status;
    }

    public void setStatus(TradeType status) {
        this.status = status;
    }

    public int getAsset() {
        return asset;
    }

    public void setAsset(int asset) {
        this.asset = asset;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    //    /**
//     * Gets the summary trade history data that can be used to form a graph for recent sell prices
//     *
//     * @return Summary data of sell listings
//     */
//    public void addExtendedTradeHistory() {
//        // Map of all trade listings
//        Map<Integer, ArrayList<String>> multiValueMap = trades.getListing();
//
//        // Iterates through multi value map to get all partially completed or completed trades
//        for (Map.Entry<Integer, ArrayList<String>> mvMap : multiValueMap.entrySet()) {
//            if(mvMap.getKey() <= multiValueMap.size()) {
//
//                // The key of the map is the trade ID
//                Integer tradeID = mvMap.getKey();
//
//                // String representation of trade status
//                String tradeStatus = multiValueMap.get(tradeID).get(tradeFulfilled);
//
//                // Checks whether trade has been completed or partially completed
//                boolean tradeCompleted = tradeStatus == "Yes" || tradeStatus == "Partial";
//
//                // TradeIDs for the extended trade history
//                int index = extendedTradeHistory.size() + 1;
//
//                // If a trade is completed or partially completed, adds it to the map
//                if(tradeCompleted) {
//                    extendedTradeHistory.put(index, multiValueMap.get(tradeID));
//
//                }
//            }
//        }
//    }
//
//    /**
//     * Gets the summary trade history data that can be used to form a graph for recent sell prices
//     *
//     * @return Summary data of sell listings
//     */
//    public Map<Integer, ArrayList<String>> getExtendedTradeHistory() {
//        addExtendedTradeHistory();
//        return extendedTradeHistory;
//    }
//
//    /**
//     * Gets the summary trade history data that can be used to form a graph for recent sell prices
//     *
//     * @return Summary data of sell listings
//     */
//    public Map<Integer, ArrayList<String>> getSummaryTradeHistory() {
//        return trades.getTradeHistory();
//    }
}