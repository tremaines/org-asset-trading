package Server;

import Client.Trades;

import java.util.HashMap;

/**
 * An interface for the trades database table
 */
public interface TradeDB {

    /**
     * Get a collection of all assets sold, including the total quantity being sold and the lowest price
     * for each asset
     * @return A HashMap with asset name as the key and an array of ints containing the total quantity being
     * sold and the lowest price
     */
    HashMap<String, int[]> getAllBuyDetails();

    /**
     * Get all trades of a specific type list by a specific unit
     * @param unitID The unit's ID
     * @param type The trade type
     * @return A HashMap with the trade ID and a string array containing the asset name, quantity and price
     */
    HashMap<Integer, String[]> getTradesByUnit(int unitID, String type);

    /**
     * Add a new trade
     * @param trade
     */
    void addTrade(Trades trade);

    /**
     * Match a sell order
     * @param assetID The asset's ID
     * @param price The price listed
     * @return The ID of the matching trade, 0 if no match, -1 if there is an error
     */
    int matchSell(int assetID, int price);

    /**
     * Match a buy order
     * @param assetID The asset's ID
     * @param price The price listed
     * @return The ID of the matching trade, 0 if no match, -1 if there is an error
     */
    int matchBuy(int assetID, int price);

    /**
     * Get details of a trade
     * @param tradeID The trade's ID
     * @return A Trades object
     */
    Trades getTrade(int tradeID);

    /**
     * Get all trades of a specific type
     * @param type The trade type ('buy' or 'sell')
     * @return A HashMap with the trade ID and a string array containing the asset name, quantity and price
     */
    HashMap<Integer, String[]> getTypeOfTrade(String type);

    /**
     * Get all trades of a specific type and a specific asset
     * @param type The trade type ('buy' or 'sell')
     * @param assetName The asset's name
     * @return A HashMap with the trade ID and a string array containing the asset name, quantity and price
     */
    HashMap<Integer, String[]> getByAssetAndType(String type, String assetName);

    /**
     * Update an existing trade
     * @param trade
     */
    void update(Trades trade);

    /**
     * Delete an existing trade
     * @param tradeID
     */
    void delete(int tradeID);

    /**
     * Gets the current highest trade ID
     * @return The highest trade ID
     */
    int getTradeID();
}
