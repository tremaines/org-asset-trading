package Server;

import Client.Assets;
import Client.AssetsException;

/**
 * An interface for the trades assets_owned/purchases table
 */
public interface PurchasesDB {

    /**
     * Add or update asset/unit ownership details
     * @param asset The asset's ID
     * @param unit The unit's ID
     * @param qty The quantity owned
     * @param replace If the relationship already exists, True will replace the existing quantity. False will add
     *                the new quantity to the old quantity.
     */
    void addToPurchases(int asset, int unit, int qty, boolean replace) throws AssetsException;

    /**
     * Get all assets owned by a specific unit
     * @param unitID The unit's ID
     * @return An array of Assets objects, each object contains the asset ID, the name of the asset
     * and the quantity owned by the unit
     */
    Assets[] getAssetsByUnit(int unitID);

    /**
     * Get all assets, with the quantity owned being relevant to the unit passed into the method (all others will be 0)
     * @param unitID The unit's ID
     * @return An array of Assets object, each object contains the asset ID, the name of the asset
     * and the quantity owned if owned, 0 otherwise.
     */
    Assets[] getOwnedAndUnowned(int unitID);

    /**
     * Get the details of a specific asset owned by a specific unit
     * @param assetID The asset's ID
     * @param unitID The unit's ID
     * @return An instance of Assets that contains the asset's ID, name and the quantity owned by the unit
     */
    Assets getAsset(int assetID, int unitID);
}
