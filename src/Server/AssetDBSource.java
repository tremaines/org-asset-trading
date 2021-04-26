package Server;

import Client.Assets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//TODO: Need to deal with the assets_purchased table
/***
 * A wrapper class for accessing the assets_produced table
 */
public class AssetDBSource {
    // SELECT statements
    private static final String GET_ASSET = "SELECT * FROM assets_produced WHERE asset_id=?";
    private static final String GET_NAMES = "SELECT asset_name FROM assets_produced;";

    // Prepared Statements
    private PreparedStatement getAsset;
    private PreparedStatement getNames;

    private Connection connection;

    /***
     * Constructor
     * @param connection Takes a connection the trading_platform database.
     */
    public AssetDBSource(Connection connection) {
        this.connection = connection;

        try{
            getAsset = connection.prepareStatement(GET_ASSET);
            getNames = connection.prepareStatement(GET_NAMES);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Gets the details of an asset stored in the database based on its ID
     *
     * @param id The asset ID as a string
     * @return An instance of the Assets class containing the details of the asset found in the database
     */
    public Assets getAsset(String id) {
        Assets asset = new Assets();
        ResultSet rs = null;

        try {
            getAsset.setString(1, id);
            rs = getAsset.executeQuery();
            rs.next();

            asset.setAssetID(rs.getString("asset_id"));
            asset.setAssetName(rs.getString("asset_name"));
            asset.setQuantity(rs.getInt("quantity"));
            asset.setUnitID(rs.getString("unit"));
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
}
