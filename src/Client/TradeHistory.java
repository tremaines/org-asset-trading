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
     * Keeps track of the full trade history of completed buy and sell listings, and stores it
     * into a multi valued map.
     */
    public void addExtendedTradeHistory() {
        Map<Integer, ArrayList<String>> multiValueMap = trades.getListing();

        // Iterates through multi value map to get all partially completed or completed trades
        for (Map.Entry<Integer, ArrayList<String>> mvMap : multiValueMap.entrySet()) {
            for (Map.Entry<Integer, ArrayList<String>> map : multiValueMap.entrySet()) {

                // The key of the map is the trade ID
                Integer tradeID = map.getKey();

                String tradeStatus = multiValueMap.get(tradeID).get(tradeFulfilled);
                boolean tradeCompleted = tradeStatus == "Yes" || tradeStatus == "Partially";

                // TradeIDs for the extended trade history
                int index = multiValueMap.size() + 1;

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