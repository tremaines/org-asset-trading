package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A wrapper for the assets_purchased table. This table deals with the assets an organisational unit has purchased that
 * they DON'T produce. I am working on the assumption that units buy assets and don't re-sell them but this table may
 * allow for that
 */
public class PurchasesDBSource {

    // SQL Statements
    private static final String ADD = "INSERT INTO assets_purchased (asset_id, unit, quantity)" +
            " VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE assets_purchased SET quantity=? WHERE asset_id=? AND unit=?;";
    private static final String SELECT = "SELECT * FROM assets_purchased WHERE asset_id=? and unit=?;";

    // Prepared Statements
    private PreparedStatement add;
    private PreparedStatement update;
    private PreparedStatement select;

    private Connection connection;

    /**
     * Constructor
     * @param connection A connection to the database
     */
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

    /**
     * Add a new unit/asset combination to the purchases table
     * @param asset The asset id
     * @param unit The unit id
     * @param qty The quantity of the purchase
     */
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

    /**
     * Update the quantity of an asset a unit has purchased
     * @param asset The asset id
     * @param unit The unit od
     * @param oldQty The old quantity
     * @param addedQty The additional quantity
     */
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

    /**
     * Takes an asset, unit and quantity and either adds the unit/asset combination to the purchased_assets table
     * if it is a new combination or adds the new quantity to the existing quantity for that combination
     *
     * @param asset The asset id
     * @param unit The unit id
     * @param qty The quantity of the asset
     */
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
