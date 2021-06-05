package Server;

import Client.Assets;
import Client.AssetsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/***
 * A wrapper class for accessing the assets table
 */
public class AssetDBSource implements AssetDB {
    // SQL statements
    private static final String GET_ASSET = "SELECT * FROM assets;";
    private static final String GET_ASSET_BY_NAME = "SELECT * FROM assets WHERE asset_name=?;";
    private static final String UPDATE = "UPDATE assets SET asset_name = ?, quantity = ? " +
            "WHERE asset_id= ?;";
    private static final String ADD = "INSERT INTO assets (asset_name) VALUES(?);";

    // Prepared Statements
    private PreparedStatement getAsset;
    private PreparedStatement getAssetByName;
    private PreparedStatement update;
    private PreparedStatement add;

    private Connection connection;

    /***
     * Constructor
     */
    public AssetDBSource() {
        this.connection = DBConnection.getConnection();

        try{
            getAsset = connection.prepareStatement(GET_ASSET);
            getAssetByName = connection.prepareStatement(GET_ASSET_BY_NAME);
            update = connection.prepareStatement(UPDATE);
            add = connection.prepareStatement(ADD);
        } catch (SQLException sqle) {
            System.err.println(sqle);

        }
    }

    /**
     * Add a new asset to the table
     * @param asset The asset object to be added
     */
    public void add(Assets asset) {
        try {
            add.setString(1, asset.getAssetName());
            add.execute();
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Gets all the assets from the assets table
     *
     * @return An array of Assets objects, each containing the ID and name of the asset
     */
    public Assets[] getAllAssets() {
        ArrayList<Assets> assets = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = getAsset.executeQuery();
            while(rs.next()) {
                Assets asset = new Assets();
                asset.setAssetID(rs.getInt("asset_id"));
                asset.setAssetName(rs.getString("asset_name"));
                assets.add(asset);
            }
        } catch(SQLException | AssetsException sqle){
            System.err.println(sqle);
        }

        return assets.toArray(new Assets[0]);
    }

    /**
     * Get an asset from the assets table based on its name
     *
     * @param name The name of the asset
     * @return An instance of the Assets class containing the ID and name of the asset
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
        } catch(SQLException | AssetsException sqle){
            System.err.println(sqle);
        }

        return asset;
    }

    /**
     * Updates the details of an asset that already exists in the assets table
     * @param asset The asset to be updated
     */
    public void update(Assets asset) {
        try{
            update.setString(1, asset.getAssetName());
            update.setInt(2, asset.getQuantity());
            update.setInt(3, asset.getAssetID());
            update.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
