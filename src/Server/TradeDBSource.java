package Server;

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
    private static final String GET_TRADES = "SELECT asset_name, SUM(trades.quantity), MIN(price) " +
            "FROM trades INNER JOIN assets_produced WHERE type='sell' GROUP BY asset;";

    // Prepared statements
    private PreparedStatement getTrades;

    private Connection connection;

    /***
     * Constructor that takes a connection to the trading_platform database
     * @param connection Connection the to the trading_platform database
     */
    public TradeDBSource(Connection connection) {
        this.connection = connection;

        try{
            getTrades = connection.prepareStatement(GET_TRADES);
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
            rs = getTrades.executeQuery();
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
}
