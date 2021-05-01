package Server;

import Client.TradeHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoryDBSource {

    private static final String ADD = "INSERT INTO trade_history (status, asset, qty, date, seller, buyer) " +
            "VALUES(?, ?, ?, NOW(), ?, ?);";

    private PreparedStatement add;

    Connection connection;

    public HistoryDBSource(Connection connection) {
        this.connection = connection;

        try{
            add = connection.prepareStatement(ADD);
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }

    public void addToHistory(TradeHistory trade) {
        try {
            add.setString(1, trade.getStatus().name());
            add.setInt(2, trade.getAsset());
            add.setInt(3, trade.getQty());
            add.setString(4, trade.getSeller());
            add.setString(5, trade.getBuyer());
            add.execute();
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
