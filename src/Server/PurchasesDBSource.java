package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchasesDBSource {

    private static final String ADD = "INSERT INTO assets_purchased (asset_id, unit, quantity)" +
            " VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE assets_purchased SET quantity=? WHERE asset_id=? AND unit=?;";
    private static final String SELECT = "SELECT * FROM assets_purchased WHERE asset_id=? and unit=?;";

    private PreparedStatement add;
    private PreparedStatement update;
    private PreparedStatement select;

    private Connection connection;

    public PurchasesDBSource(Connection connection) {
        this.connection = connection;

        try {
            add = connection.prepareStatement(ADD);
            update = connection.prepareStatement(UPDATE);
            select = connection.prepareStatement(SELECT);
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }

    private void add(int asset, int unit, int qty) {
        try {
            add.setInt(1, asset);
            add.setInt(2, unit);
            add.setInt(3, qty);
            add.execute();
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }


    private void update(int asset, int unit, int oldQty, int addedQty) {
        try {
            update.setInt(1, (oldQty + addedQty));
            update.setInt(2, asset);
            update.setInt(3, unit);
            update.execute();
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }

    public void addToPurchases(int asset, int unit, int qty) {
        ResultSet rs = null;
        try{
            select.setInt(1, asset);
            select.setInt(2, unit);
            rs = select.executeQuery();

            if (rs.next()) {
                int oldQty = rs.getInt("quantity");
                this.update(asset, unit, oldQty, qty);
            }
            else {
                this.add(asset, unit, qty);
            }
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
