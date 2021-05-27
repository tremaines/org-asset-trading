package Server;

import Client.Assets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A wrapper for the assets_purchased table. This table deals with the assets an organisational unit has purchased that
 * they DON'T produce. I am working on the assumption that units buy assets and don't re-sell them but this table may
 * allow for that
 */
public class PurchasesDBSource {

    // SQL Statements
    private static final String ADD = "INSERT INTO assets_owned (asset_id, unit, quantity)" +
            " VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE assets_owned SET quantity=? WHERE asset_id=? AND unit=?;";
    private static final String SELECT = "SELECT * FROM assets_owned WHERE asset_id=? and unit=?;";
    private static final String GET_ASSETS_BY_UNIT = "SELECT assets_owned.asset_id, unit, quantity, asset_name " +
            "FROM assets_owned INNER JOIN assets on assets_owned.asset_id = assets.asset_id " +
            "WHERE unit=?;";
    private static final String GET_ASSET_UNIT_COMBO = "SELECT assets_owned.asset_id, unit, quantity, asset_name " +
            "FROM assets_owned INNER JOIN assets on assets_owned.asset_id = assets.asset_id " +
            "WHERE unit=? and assets_owned.asset_id=?;";

    // Prepared Statements
    private PreparedStatement add;
    private PreparedStatement update;
    private PreparedStatement select;
    private PreparedStatement getAssetsByUnit;
    private PreparedStatement getAssetUnitCombo;

    private Connection connection;

    /**
     * Constructor
     */
    public PurchasesDBSource() {
        this.connection = DBConnection.getConnection();

        try {
            add = connection.prepareStatement(ADD);
            update = connection.prepareStatement(UPDATE);
            select = connection.prepareStatement(SELECT);
            getAssetsByUnit = connection.prepareStatement(GET_ASSETS_BY_UNIT);
            getAssetUnitCombo = connection.prepareStatement(GET_ASSET_UNIT_COMBO);
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
     * Return an array of all assets owned by a unit
     *
     * @param id The id of the unit
     * @return An array of strings (the name of the assets)
     */
    public String[] getAssetNamesByUnit(int id) {
        ArrayList<String> names = new ArrayList<String>();
        ResultSet rs = null;

        try {
            getAssetsByUnit.setInt(1, id);
            rs = getAssetsByUnit.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("asset_name"));
            }
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return names.toArray(new String[0]);
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
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
        return assets.toArray(new Assets[0]);
    }

    /**
     * Takes an unit id and returns each assets and the quantity available
     * @param id The id of the unit
     * @return A hashmap in which the asset name is the key and the quantity of the asset is the value
     */
    public HashMap<String, Integer> getAssetsAndAmounts(int id) {
        HashMap<String, Integer> assets = new HashMap<>();
        ResultSet rs = null;

        try {
            getAssetsByUnit.setInt(1, id);
            rs = getAssetsByUnit.executeQuery();
            while (rs.next()) {
                String name = rs.getString("asset_name");
                int qty = rs.getInt("quantity");
                assets.put(name, qty);
            }

            return assets;
        } catch(SQLException sqle) {
            System.err.println(sqle);
            return null;
        }
    }

    //TODO
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
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return asset;
    }
}
