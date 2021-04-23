package Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Trade History gives the information about the asset and its trade
 * value along with the users that have traded it for users to know if they should
 * buy that asset.
 */
public class TradeHistory {

    // Collection of all instances of the Trades class
    private Trades trades;
    // Trade fulfilled status (Yes/No/Partially/Cancelled)
    private static final int tradeFulfilled = 7;
    // Multi valued map of the entire trade history
    Map<Integer, ArrayList<String>> extendedTradeHistory = new HashMap<>();

    // Constructor for the TradeHistory class that takes a collection of all trades
    public TradeHistory(Trades trades)
    {
        this.trades = trades;
    }

    /**
     * Gets the summary trade history data that can be used to form a graph for recent sell prices
     *
     * @return Summary data of sell listings
     */
    public void addExtendedTradeHistory() {
        // Map of all trade listings
        Map<Integer, ArrayList<String>> multiValueMap = trades.getListing();

        // Iterates through multi value map to get all partially completed or completed trades
        for (Map.Entry<Integer, ArrayList<String>> mvMap : multiValueMap.entrySet()) {
            if(mvMap.getKey() <= multiValueMap.size()) {

                // The key of the map is the trade ID
                Integer tradeID = mvMap.getKey();

                // String representation of trade status
                String tradeStatus = multiValueMap.get(tradeID).get(tradeFulfilled);

                // Checks whether trade has been completed or partially completed
                boolean tradeCompleted = tradeStatus == "Yes" || tradeStatus == "Partial";

                // TradeIDs for the extended trade history
                int index = extendedTradeHistory.size() + 1;

                // If a trade is completed or partially completed, adds it to the map
                if(tradeCompleted) {
                    extendedTradeHistory.put(index, multiValueMap.get(tradeID));

                }
            }
        }
    }

    /**
     * Gets the summary trade history data that can be used to form a graph for recent sell prices
     *
     * @return Summary data of sell listings
     */
    public Map<Integer, ArrayList<String>> getExtendedTradeHistory() {
        addExtendedTradeHistory();
        return extendedTradeHistory;
    }

    /**
     * Gets the summary trade history data that can be used to form a graph for recent sell prices
     *
     * @return Summary data of sell listings
     */
    public Map<Integer, ArrayList<String>> getSummaryTradeHistory() {
        return trades.getTradeHistory();
    }
}