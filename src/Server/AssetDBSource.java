package Server;

import Client.Assets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//TODO: Need to deal with the assets_purchased table
/***
 * A wrapper class for accessing the assets_produced table
 */
public class AssetDBSource {
    // SELECT statements
    private static final String GET_ASSET = "SELECT * FROM assets_produced WHERE asset_id=?";
    private static final String GET_ASSET_BY_NAME = "SELECT * FROM assets_produced WHERE asset_name=?;";
    private static final String GET_ASSETS_BY_UNIT = "SELECT * FROM assets_produced WHERE unit=?;";
    private static final String GET_NAMES = "SELECT asset_name FROM assets_produced;";
    private static final String UPDATE = "UPDATE assets_produced SET asset_name = ?, quantity = ?, unit = ? " +
            "WHERE asset_id= ?;";
    private static final String ADD = "INSERT INTO assets_produced (asset_name, quantity, unit) VALUES(?, ?, ?);";

    // Prepared Statements
    private PreparedStatement getAsset;
    private PreparedStatement getAssetByName;
    private PreparedStatement getAssetsByUnit;
    private PreparedStatement getNames;
    private PreparedStatement update;
    private PreparedStatement add;

    private Connection connection;

    /***
     * Constructor
     * @param connection Takes a connection the trading_platform database.
     */
    public AssetDBSource(Connection connection) {
        this.connection = connection;

        try{
            getAsset = connection.prepareStatement(GET_ASSET);
            getAssetByName = connection.prepareStatement(GET_ASSET_BY_NAME);
            getAssetsByUnit = connection.prepareStatement(GET_ASSETS_BY_UNIT);
            getNames = connection.prepareStatement(GET_NAMES);
            update = connection.prepareStatement(UPDATE);
            add = connection.prepareStatement(ADD);
        } catch (SQLException sqle) {
            System.err.println(sqle);

        }
    }

    public void add(Assets asset) {
        try {
            add.setString(1, asset.getAssetName());
            add.setInt(2, asset.getQuantity());
            add.setInt(3, asset.getUnitID());
            add.execute();
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Gets the details of an asset stored in the database based on its ID
     *
     * @param id The asset ID as a string
     * @return An instance of the Assets class containing the details of the asset found in the database
     */
    public Assets getAsset(int id) {
        Assets asset = new Assets();
        ResultSet rs = null;

        try {
            getAsset.setInt(1, id);
            rs = getAsset.executeQuery();
            rs.next();

            asset.setAssetID(rs.getInt("asset_id"));
            asset.setAssetName(rs.getString("asset_name"));
            asset.setQuantity(rs.getInt("quantity"));
            asset.setUnitID(rs.getInt("unit"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return asset;
    }

    //TODO: Not a huge fan of this as asset name is not guaranteed to be unique... But for the purposes of this
    //TODO: assignment, yolo
    /**
     * Get an asset based on its name
     *
     * @param name The name of the asset
     * @return An instance of the Assets class
     */
    public Assets getAsset(String name) {
        Assets asset = new Assets();
        ResultSet rs = null;

        try {
            getAssetByName.setString(1, name);
            rs = getAssetByName.executeQuery();
            rs.next();

            asset.setAssetID(rs.getInt("asset_id"));
            asset.setAssetName(rs.getString("asset_name"));
            asset.setQuantity(rs.getInt("quantity"));
            asset.setUnitID(rs.getInt("unit"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return asset;
    }

    /***
     * Gets a list of all assets produced by the units of the organisation
     * @return An array of Strings containing the names of the assets available for purchase
     */
    public String[] getAssetNames() {
        ArrayList<String> names = new ArrayList<String>();
        ResultSet rs = null;

        try {
            rs = getNames.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("asset_name"));
            }
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return names.toArray(new String[0]);
    }

    public void update(Assets asset) {
        try{
            update.setString(1, asset.getAssetName());
            update.setInt(2, asset.getQuantity());
            update.setInt(3, asset.getUnitID());
            update.setInt(4, asset.getAssetID());
            update.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Return an array of all assets produced by a unit
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
}
