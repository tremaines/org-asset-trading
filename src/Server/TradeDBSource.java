package Server;

import Client.Trades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//TODO: This only deals with open trades, need to suss out trade history stuff later
/***
 * A wrapper class for accessing the Trades table in the database
 */
public class TradeDBSource {
    // SELECT statements
    private static final String GET_TRADES_BY_ASSET = "SELECT asset_name, SUM(trades.quantity), MIN(price) " +
            "FROM trades INNER JOIN assets_produced WHERE type='sell' GROUP BY asset;";
    private static final String ADD_TRADE = "INSERT INTO trades (type, user, asset, quantity, price, date) " +
            "VALUES (?, ?, ?, ?, ?, NOW());";
    private static final String GET_MATCHING_SELLS = "SELECT MIN(trade_id), MIN(price), quantity " +
            "from trades WHERE asset=? AND type='sell' AND price <=?;";
    private static final String GET_MATCHING_BUYS = "SELECT MIN(trade_id), price, quantity " +
            "from trades WHERE asset=? AND type='sell' AND price >=?;";
    private static final String GET_TRADE = "SELECT * FROM trades WHERE trade_id=?;";
    private static final String UPDATE_QTY = "UPDATE trades SET quantity = ? WHERE trade_id = ?;";
    private static final String DELETE = "DELETE FROM trades WHERE trade_id=?;";

    // Prepared statements
    private PreparedStatement getTradesByAsset;
    private PreparedStatement addTrades;
    private PreparedStatement getMatchingSells;
    private PreparedStatement getMatchingBuys;
    private PreparedStatement getTrade;
    private PreparedStatement updateQty;
    private PreparedStatement delete;

    private Connection connection;

    /***
     * Constructor that takes a connection to the trading_platform database
     * @param connection Connection the to the trading_platform database
     */
    public TradeDBSource(Connection connection) {
        this.connection = connection;

        try{
            getTradesByAsset = connection.prepareStatement(GET_TRADES_BY_ASSET);
            addTrades = connection.prepareStatement(ADD_TRADE);
            getMatchingSells = connection.prepareStatement(GET_MATCHING_SELLS);
            getMatchingBuys = connection.prepareStatement(GET_MATCHING_BUYS);
            getTrade = connection.prepareStatement(GET_TRADE);
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
    public HashMap<String, int[]> getTrades() {
        int[] qtyAndPrice = new int[2];
        HashMap<String, int[]> trades = new HashMap<>();
        ResultSet rs = null;

        try {
            rs = getTradesByAsset.executeQuery();
            while (rs.next()) {
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
     * Add a new trade to the database. Trade ID and Date should be added automatically by MySQL
     *
     * @param trade The new trade to be added
     */
    public void addTrade(Trades trade) {
        try {
            addTrades.setString(1, trade.getType().name());
            addTrades.setString(2, trade.getUserName());
            addTrades.setInt(3, trade.getAssetId());
            addTrades.setInt(4, trade.getQuantity());
            addTrades.setInt(5, trade.getPrice());
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
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return trade;
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
}
