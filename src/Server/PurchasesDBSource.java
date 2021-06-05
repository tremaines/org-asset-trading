package Server;

import Client.Assets;
import Client.AssetsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A wrapper for the assets_purchased table. This table deals with the assets an organisational unit has purchased that
 * they DON'T produce. I am working on the assumption that units buy assets and don't re-sell them but this table may
 * allow for that
 */
public class PurchasesDBSource implements PurchasesDB {

    // SQL Statements
    private static final String ADD = "INSERT INTO assets_owned (asset_id, unit, quantity)" +
            " VALUES(?, ?, ?);";
    private static final String DELETE = "DELETE FROM assets_owned WHERE asset_id=? AND unit=?;";
    private static final String UPDATE = "UPDATE assets_owned SET quantity=? WHERE asset_id=? AND unit=?;";
    private static final String SELECT = "SELECT * FROM assets_owned WHERE asset_id=? and unit=?;";
    private static final String GET_ASSETS_BY_UNIT = "SELECT assets_owned.asset_id, unit, quantity, asset_name " +
            "FROM assets_owned INNER JOIN assets on assets_owned.asset_id = assets.asset_id " +
            "WHERE unit=?;";
    private static final String GET_ASSET_UNIT_COMBO = "SELECT assets_owned.asset_id, unit, quantity, asset_name " +
            "FROM assets_owned INNER JOIN assets on assets_owned.asset_id = assets.asset_id " +
            "WHERE unit=? and assets_owned.asset_id=?;";
    private static final String GET_OWNED_AND_UNOWNED = "SELECT assets.asset_id, assets.asset_name, assets_owned.quantity " +
            "FROM assets " +
            "LEFT JOIN (SELECT asset_id, quantity " +
            "FROM assets_owned " +
            "WHERE unit=? " +
            "GROUP BY asset_id) as assets_owned " +
            "ON assets.asset_id = assets_owned.asset_id;";



    // Prepared Statements
    private PreparedStatement add;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement select;
    private PreparedStatement getAssetsByUnit;
    private PreparedStatement getAssetUnitCombo;
    private PreparedStatement getOwnedAndUnowned;

    private Connection connection;

    /**
     * Constructor
     */
    public PurchasesDBSource() {
        this.connection = DBConnection.getConnection();

        try {
            add = connection.prepareStatement(ADD);
            delete = connection.prepareStatement(DELETE);
            update = connection.prepareStatement(UPDATE);
            select = connection.prepareStatement(SELECT);
            getAssetsByUnit = connection.prepareStatement(GET_ASSETS_BY_UNIT);
            getAssetUnitCombo = connection.prepareStatement(GET_ASSET_UNIT_COMBO);
            getOwnedAndUnowned = connection.prepareStatement(GET_OWNED_AND_UNOWNED);
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
     * @param replace Mark true if you are replacing the quantity with a new one
     */
    private void update(int asset, int unit, int oldQty, int addedQty, boolean replace) {
        try {
            if (replace) {
                update.setInt(1, addedQty);
            }
            else {
                update.setInt(1, (oldQty + addedQty));
            }
            update.setInt(2, asset);
            update.setInt(3, unit);
            update.execute();
        } catch(SQLException sqle){
            System.err.println(sqle);
        }
    }

    /**
     * Removes an asset/unit relation from the table if the quantity owned is 0
     * @param asset The asset ID
     * @param unit The unit ID
     * @param qty The quantity owned
     * @return True if ownership is 0 and deletion occurred, false otherwise
     * @throws SQLException
     */
    private boolean delete(int asset, int unit, int qty) throws SQLException {
        if (qty == 0) {
            delete.setInt(1, asset);
            delete.setInt(2, unit);
            delete.execute();
            return true;
        }
        return false;
    }

    /**
     * Takes an asset, unit and quantity and either adds the unit/asset combination to the purchased_assets table
     * if it is a new combination or adds the new quantity to the existing quantity for that combination
     *
     * @param asset The asset id
     * @param unit The unit id
     * @param qty The quantity of the asset
     * @param replace Mark true if you are replacing the quantity with a new one
     */
    public void addToPurchases(int asset, int unit, int qty, boolean replace) {
        ResultSet rs = null;
        try{
            if(delete(asset, unit, qty)) {
                return;
            }

            select.setInt(1, asset);
            select.setInt(2, unit);
            rs = select.executeQuery();

            if (rs.next()) {
                int oldQty = rs.getInt("quantity");
                this.update(asset, unit, oldQty, qty, replace);
            }
            else {
                this.add(asset, unit, qty);
            }
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Get an Assets array of all assets a unit produces
     * @param unitID The unit ID
     * @return An array of assets
     */
    public Assets[] getAssetsByUnit(int unitID) {
        ArrayList<Assets> assets = new ArrayList<>();
        ResultSet rs = null;

        try {
            getAssetsByUnit.setInt(1, unitID);
            rs = getAssetsByUnit.executeQuery();
            while (rs.next()) {
                Assets asset = new Assets();
                asset.setAssetID(rs.getInt("asset_id"));
                asset.setAssetName(rs.getString("asset_name"));
                asset.setQuantity(rs.getInt("quantity"));
                asset.setUnitID(rs.getInt("unit"));
                assets.add(asset);
            }
        } catch (SQLException | AssetsException sqle) {
            System.err.println(sqle);
        }
        return assets.toArray(new Assets[0]);
    }

    /**
     * Returns an array of all assets, including the quantity of the assets owned by the
     * unit passed in
     * @param id The unit ID
     * @return An array of all assets
     */
    public Assets[] getOwnedAndUnowned(int id) {
        ArrayList<Assets> assets = new ArrayList<>();
        ResultSet rs = null;

        try {
            getOwnedAndUnowned.setInt(1, id);
            rs = getOwnedAndUnowned.executeQuery();
            while (rs.next()) {
                Assets asset = new Assets();
                asset.setAssetID(rs.getInt("asset_id"));
                asset.setAssetName(rs.getString("asset_name"));
                asset.setQuantity(rs.getInt("quantity"));
                assets.add(asset);
            }
        } catch (SQLException | AssetsException sqle) {
            System.err.println(sqle);
        }
        return assets.toArray(new Assets[0]);
    }

    /***
     * Gets the details of an asset stored in the database based on its ID
     *
     * @return An instance of the Assets class containing the details of the asset found in the database
     */
    public Assets getAsset(int assetID, int unitID) {
        Assets asset = new Assets();
        ResultSet rs = null;

        try {
            getAssetUnitCombo.setInt(1, unitID);
            getAssetUnitCombo.setInt(2, assetID);
            rs = getAssetUnitCombo.executeQuery();
            rs.next();

            asset.setAssetID(rs.getInt("asset_id"));
            asset.setUnitID(rs.getInt("unit"));
            asset.setQuantity(rs.getInt("quantity"));
            asset.setAssetName(rs.getString("asset_name"));
        } catch(SQLException | AssetsException sqle){
            System.err.println(sqle);
        }

        return asset;
    }
}
