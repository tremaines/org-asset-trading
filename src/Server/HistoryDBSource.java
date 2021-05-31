package Server;

import Client.TradeHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A wrapper for interacting with the trade_history table in the database
 */
public class HistoryDBSource implements HistoryDB {

    // SQL Statements
    private static final String ADD = "INSERT INTO trade_history (status, asset, qty, date, seller, buyer, credits) " +
            "VALUES(?, ?, ?, NOW(), ?, ?, ?);";
    private static final String GET_BY_ASSET = "SELECT qty, credits " +
            "FROM trade_history " +
            "JOIN assets on trade_history.asset = assets.asset_id " +
            "WHERE status='complete' and asset_name=?;";

    // Prepared Statements
    private PreparedStatement add;
    private PreparedStatement getByAsset;

    Connection connection;

    /**
     * Constructor for the database table wrapper
     */
    public HistoryDBSource() {
        this.connection = DBConnection.getConnection();

        try{
            add = connection.prepareStatement(ADD);
            getByAsset = connection.prepareStatement(GET_BY_ASSET);
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }

    /**
     * Adds a new trade to the trade_history table
     * @param trade The trade history object to be added
     */
    public void addToHistory(TradeHistory trade) {
        try {
            add.setString(1, trade.getStatus().name());
            add.setInt(2, trade.getAsset());
            add.setInt(3, trade.getQty());
            add.setString(4, trade.getSeller());
            add.setString(5, trade.getBuyer());
            add.setInt(6, trade.getCredits());
            add.execute();
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Gets the quantity and price of completed trades for an asset
     * @param assetName The asset for which you want the trade history
     * @return An array of ints, each array contains the quantity and credits values
     */
    public ArrayList<int[]> getHistoryOfAsset(String assetName) {
        ArrayList<int[]> allValues = new ArrayList<>();

        ResultSet rs = null;

        try {
            getByAsset.setString(1, assetName);
            rs = getByAsset.executeQuery();
            while (rs.next()) {
                int[] values = new int[2];
                values[0] = rs.getInt("qty");
                values[1] = rs.getInt("credits");

                allValues.add(values);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
        return allValues;
    }
}
