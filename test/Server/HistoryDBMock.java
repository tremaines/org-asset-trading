package Server;

import Client.TradeHistory;
import Client.TradesException;

import java.util.ArrayList;

/**
 * A mock implementation of the trade_history table database
 */
public class HistoryDBMock implements HistoryDB{

    int currentID;
    ArrayList<TradeHistory> history;

    public HistoryDBMock() {

        history = new ArrayList<>();
        currentID = 1;
    }

    @Override
    public void addToHistory(TradeHistory trade) throws TradesException {
        trade.setId(currentID);
        currentID++;
        history.add(trade);
    }

    @Override
    public ArrayList<int[]> getHistoryOfAsset(String assetName) {
        return null;
    }

    public TradeHistory getRecord(int recordID) {
        for (TradeHistory trade : history) {
            if (trade.getId() == recordID) {
                return trade;
            }
        }
        return null;
    }
}
