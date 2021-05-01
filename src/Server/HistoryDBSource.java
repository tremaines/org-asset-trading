package Server;

import Client.TradeHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A wrapper for interacting with the trade_history table in the database
 */
public class HistoryDBSource {

    // SQL Statements
    private static final String ADD = "INSERT INTO trade_history (status, asset, qty, date, seller, buyer, credits) " +
            "VALUES(?, ?, ?, NOW(), ?, ?, ?);";

    // Prepared Statements
    private PreparedStatement add;

    Connection connection;

    /**
     * Constructor for the database table wrapper
     * @param connection A connection to the MariaDB database
     */
    public HistoryDBSource(Connection connection) {
        this.connection = connection;

        try{
            add = connection.prepareStatement(ADD);
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }

    /**
     * Adds a new trade to the trade history table
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
}
