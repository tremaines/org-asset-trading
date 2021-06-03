package Server;

import Client.Assets;

/**
 * An interface for the assets database table
 */
public interface AssetDB {

    /**
     * Add a new asset
     * @param asset The asset to be added
     */
    public void add(Assets asset);

    /**
     * Get all assets
     * @return An array of Assets objects
     */
    public Assets[] getAllAssets();

    /**
     * Get an asset based on its name
     * @param name The name of the asset
     * @return An Assets object
     */
    public Assets getAsset(String name);

    /**
     * Update an existing asset
     * @param asset An Assets object with updated details
     */
    public void update(Assets asset);
}
