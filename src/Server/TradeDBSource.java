package Server;

import Client.Trades;
import Client.TradesException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/***
 * A wrapper class for accessing the Trades table in the database
 */
public class TradeDBSource implements TradeDB {

    // SELECT statements
    private static final String GET_TRADES_BY_ASSET = "SELECT asset_name, SUM(trades.quantity), MIN(price) " +
            "FROM trades " +
            "JOIN assets ON trades.asset = assets.asset_id " +
            "WHERE type='sell' GROUP BY asset;";
    private static final String GET_TRADES_BY_UNIT = "" +
            "SELECT trade_id, assets.asset_name, trades.quantity, trades.price " +
            "FROM trades " +
            "JOIN users ON trades.user = users.user_name " +
            "JOIN  units ON users.unit = units.unit_id " +
            "JOIN assets on trades.asset = assets.asset_id " +
            "WHERE unit_id = ? and trades.type = ?;";
    private static final String ADD_TRADE = "INSERT INTO trades (trade_id, type, user, asset, quantity, price, date) " +
            "VALUES (?, ?, ?, ?, ?, ?, NOW());";
    private static final String GET_MATCHING_SELLS = "SELECT MIN(trade_id), MIN(price), quantity " +
            "from trades WHERE asset=? AND type='sell' AND price <=?;";
    private static final String GET_MATCHING_BUYS = "SELECT MIN(trade_id), price, quantity " +
            "from trades WHERE asset=? AND type='buy' AND price >=?;";
    private static final String GET_TRADE = "SELECT * FROM trades WHERE trade_id=?;";
    private static final String GET_LARGEST_TRADE_ID = "SELECT MAX(trade_id) AS max_id FROM trades";
    private static final String GET_TYPE = "SELECT trade_id, asset_name, trades.quantity, price " +
            "FROM trades " +
            "JOIN assets on trades.asset = assets.asset_id " +
            "WHERE type=?;";
    private static final String GET_BY_ASSET_AND_TYPE = "SELECT trade_id, asset_name, trades.quantity, price " +
            "FROM trades " +
            "JOIN assets on trades.asset = assets.asset_id " +
            "WHERE type=? and asset_name=?;";
    private static final String UPDATE_QTY = "UPDATE trades SET quantity = ? WHERE trade_id = ?;";
    private static final String DELETE = "DELETE FROM trades WHERE trade_id=?;";

    // Prepared statements
    private PreparedStatement getTradesByAsset;
    private PreparedStatement getTradesByUnit;
    private PreparedStatement addTrades;
    private PreparedStatement getMatchingSells;
    private PreparedStatement getMatchingBuys;
    private PreparedStatement getTrade;
    private PreparedStatement getLargestTradeID;
    private PreparedStatement getType;
    private PreparedStatement getByAssetAndType;
    private PreparedStatement updateQty;
    private PreparedStatement delete;

    private Connection connection;

    /***
     * Constructor that takes a connection to the trading_platform database
     */
    public TradeDBSource() {
        this.connection = DBConnection.getConnection();

        try{
            getTradesByAsset = connection.prepareStatement(GET_TRADES_BY_ASSET);
            getTradesByUnit = connection.prepareStatement(GET_TRADES_BY_UNIT);
            addTrades = connection.prepareStatement(ADD_TRADE);
            getMatchingSells = connection.prepareStatement(GET_MATCHING_SELLS);
            getMatchingBuys = connection.prepareStatement(GET_MATCHING_BUYS);
            getTrade = connection.prepareStatement(GET_TRADE);
            getLargestTradeID = connection.prepareStatement(GET_LARGEST_TRADE_ID);
            getType = connection.prepareStatement(GET_TYPE);
            getByAssetAndType = connection.prepareStatement(GET_BY_ASSET_AND_TYPE);
            updateQty = connection.prepareStatement(UPDATE_QTY);
            delete = connection.prepareStatement(DELETE);

        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Groups all listed buys by the asset and adds up the total number of each asset for sale as well as
     * the lowest price
     *
     * @return A hash map with the asset name as the key and an int array containing the total quantity of that asset
     * for sale as well as the lowest listed price
     */
    public HashMap<String, int[]> getAllBuyDetails() {
        HashMap<String, int[]> trades = new HashMap<>();
        ResultSet rs = null;

        try {
            rs = getTradesByAsset.executeQuery();
            while (rs.next()) {
                int[] qtyAndPrice = new int[2];
                qtyAndPrice[0] = rs.getInt("sum(trades.quantity)");
                qtyAndPrice[1] = rs.getInt("MIN(price)");

                trades.put(rs.getString("asset_name"), qtyAndPrice);
            }
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return trades;
    }

    /**
     * Gets the trades made by users of a unit based on the trade type
     * @param id The id of the unit
     * @param type The type of trade (buy or sell) to look for
     * @return A hashmap where the trade id is the key and an array of Strings containing the asset name, quantity and
     * price is the value
     */
    public HashMap<Integer, String[]> getTradesByUnit(int id, String type) {
        HashMap<Integer, String[]> trades = new HashMap<>();
        ResultSet rs = null;

        try {
            getTradesByUnit.setInt(1, id);
            getTradesByUnit.setString(2, type);

            rs = getTradesByUnit.executeQuery();
            while (rs.next()) {
                String[] columns = new String[3];
                columns[0] = rs.getString("asset_name");
                // This is so ugly, have to cast it an int to class Integer in order to
                // use .toString()... Must be a better way, will do for now.
                columns[1] = ((Integer)rs.getInt("quantity")).toString();
                columns[2] = ((Integer)rs.getInt("price")).toString();

                trades.put(rs.getInt("trade_id"), columns);
            }
        } catch(SQLException sqle) {
            System.err.println(sqle + "GET TRADES BY UNIT IS THROWING AN ERROR");
            System.err.println(sqle.getLocalizedMessage());
        }
        return trades;
    }

    /**
     * Add a new trade to the database. Trade ID and Date should be added automatically by MySQL
     *
     * @param trade The new trade to be added
     */
    public void addTrade(Trades trade) {
        try {
            addTrades.setInt(1, trade.getId());
            addTrades.setString(2, trade.getType().name());
            addTrades.setString(3, trade.getUserName());
            addTrades.setInt(4, trade.getAssetId());
            addTrades.setInt(5, trade.getQuantity());
            addTrades.setInt(6, trade.getPrice());
            addTrades.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Match a sell order based on the asset (id) and price. It looks for any buy price equal to or greater than the
     * sell price and matches with the trade with the smallest trade_id (the first trade in the queue in theory)
     *
     * @param id The id of the asset being sold
     * @param price The asking price
     * @return The id of the trade that matches
     */
    public int matchSell (int id, int price) {
        ResultSet rs = null;

        try {
            getMatchingSells.setInt(1, id);
            getMatchingSells.setInt(2, price);

            rs = getMatchingSells.executeQuery();
            rs.next();
            return rs.getInt("MIN(trade_id)");

        } catch (SQLException sqle){
            System.err.println(sqle);
            return -1;
        } catch (Exception e){
            System.err.println(e);
            return -1;
        }
    }

    /**
     * Match a buy order based on the asset (id) and price. It looks for any sell price equal to or less than buy price.
     * It matches with the lowest price first then the smallest trade_id (the first trade in the queue in theory)
     *
     * @param id The id of the asset being sold
     * @param price The asking price
     * @return The id of the trade that matches
     */
    public int matchBuy (int id, int price) {
        ResultSet rs = null;

        try {
            getMatchingBuys.setInt(1, id);
            getMatchingBuys.setInt(2, price);

            rs = getMatchingBuys.executeQuery();
            rs.next();
            return rs.getInt("MIN(trade_id)");

        } catch (SQLException sqle){
            System.err.println(sqle);
            return -1;
        } catch (Exception e){
            System.err.println(e);
            return -1;
        }
    }

    /**
     * Retrieves the details of a trade based on a given trade id
     *
     * @param id The trade id
     * @return And instance of the Trade class
     */
    public Trades getTrade(int id) {
        Trades trade = new Trades();
        ResultSet rs = null;

        try {
            getTrade.setInt(1, id);
            rs = getTrade.executeQuery();
            rs.next();

            trade.setId(rs.getInt("trade_id"));
            trade.setType(Trades.TradeType.valueOf(rs.getString("type")));
            trade.setUserName(rs.getString("user"));
            trade.setAssetId(rs.getInt("asset"));
            trade.setQuantity(rs.getInt("quantity"));
            trade.setPrice(rs.getInt("price"));
            trade.setDate(rs.getDate("date"));
        } catch(SQLException | TradesException sqle){
            System.err.println(sqle);
        }

        return trade;
    }

    /**
     * Get all trades of a certain type
     * @param type Type of trade
     * @return A HashMap with trade ID as the key and an array of strings as the value
     */
    public HashMap<Integer, String[]> getTypeOfTrade(String type) {
        HashMap<Integer, String[]> allTrades = new HashMap<Integer, String[]>();
        ResultSet rs = null;

        try {
            getType.setString(1, type);
            rs = getType.executeQuery();
            while (rs.next()) {
                String[] columns = new String[3];
                columns[0] = rs.getString("asset_name");
                columns[1] = ((Integer)rs.getInt("quantity")).toString();
                columns[2] = ((Integer)rs.getInt("price")).toString();

                allTrades.put(rs.getInt("trade_id"), columns);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
        return allTrades;
    }

    /**
     * Get all trades of a certain type and asset
     * @param type The type of trade
     * @param assetName The asset being traded
     * @return A HashMap with trade ID as the key and an array of strings as the value
     */
    public HashMap<Integer, String[]> getByAssetAndType(String type, String assetName) {
        HashMap<Integer, String[]> allTrades = new HashMap<Integer, String[]>();
        ResultSet rs = null;

        try {
            getByAssetAndType.setString(1, type);
            getByAssetAndType.setString(2, assetName);
            rs = getByAssetAndType.executeQuery();
            while (rs.next()) {
                String[] columns = new String[3];
                columns[0] = rs.getString("asset_name");
                columns[1] = ((Integer)rs.getInt("quantity")).toString();
                columns[2] = ((Integer)rs.getInt("price")).toString();

                allTrades.put(rs.getInt("trade_id"), columns);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
        return allTrades;
    }

    /**
     * Updates a relation in the trades table based on a given instance of the Trades class
     *
     * @param trade The trade to be updated
     */
    public void update(Trades trade) {
        try{
            updateQty.setInt(1, trade.getQuantity());
            updateQty.setInt(2, trade.getId());
            updateQty.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Deletes a trade based on a given trade id
     *
     * @param id The id of the trade to be deleted
     */
    public void delete(int id) {
        try {
            delete.setInt(1, id);
            delete.execute();
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }

    /**
     * Counts the number of listings in the database and generates a trade ID in ascending order
     *
     * @return Current trade ID to be assigned to new listing
     */
    public int getTradeID() {
        ResultSet rs = null;
        int currentTradeID = 1;
        try {
            rs = getLargestTradeID.executeQuery();

            if(rs.next()) {
                var count = rs.getInt("max_id");
                if(count > 0) {
                    currentTradeID = count + 1;
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }

        return currentTradeID;
    }
}
