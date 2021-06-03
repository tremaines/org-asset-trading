package Server;

import Client.Trades;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A mock implementation of the trades table database
 */
public class TradeDBMock implements TradeDB {
    private int currentTradeID;

    private final ArrayList<Trades> trades;

    public TradeDBMock(ArrayList<Trades> trades) {
        this.trades = trades;
        currentTradeID = trades.size();
    }

    @Override
    public HashMap<String, int[]> getAllBuyDetails() {
        return null;
    }

    @Override
    public HashMap<Integer, String[]> getTradesByUnit(int unitID, String type) {
        return null;
    }

    @Override
    public void addTrade(Trades trade) {
        trades.add(trade);
    }

    @Override
    public int matchSell(int assetID, int price) {
        Trades matchingTrade = null;
        for(Trades trade : trades) {
            if (trade.getType() == Trades.TradeType.sell && trade.getAssetId() == assetID && trade.getPrice() <= price) {
                if (matchingTrade == null) {
                    matchingTrade = trade;
                } else {
                    if (trade.getPrice() < matchingTrade.getPrice()) {
                        matchingTrade = trade;
                    }
                }
            }
        }
        if (matchingTrade != null) {
            return matchingTrade.getId();
        } else {
            return 0;
        }
    }

    @Override
    public int matchBuy(int assetID, int price) {
        Trades matchingTrade = null;
        for(Trades trade : trades) {
            if (trade.getType() == Trades.TradeType.buy && trade.getAssetId() == assetID && trade.getPrice() >= price) {
                matchingTrade = trade;
                break;
            }
        }
        if (matchingTrade != null) {
            return matchingTrade.getId();
        } else {
            return 0;
        }
    }

    @Override
    public Trades getTrade(int tradeID) {
        for(Trades trade: trades) {
            if (trade.getId() == tradeID) {
                return trade;
            }
        }

        return null;
    }

    @Override
    public HashMap<Integer, String[]> getTypeOfTrade(String type) {
        return null;
    }

    @Override
    public HashMap<Integer, String[]> getByAssetAndType(String type, String assetName) {
        return null;
    }

    @Override
    public void update(Trades trade) {
        for (int i = 0; i < trades.size(); i++) {
            if (trades.get(i).getId() == trade.getId()) {
                trades.set(i, trade);
                return;
            }
        }

    }

    @Override
    public void delete(int tradeID) {
        for(int i = 0; i < trades.size(); i++) {
            if (trades.get(i).getId() == tradeID) {
                trades.remove(i);
                return;
            }
        }
    }

    @Override
    public int getTradeID() {
        currentTradeID++;
        return currentTradeID;
    }
}
