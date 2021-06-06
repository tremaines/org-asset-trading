package Client;

import java.io.Serializable;

/**
 * Assets are available to be purchased or sold on the electronic marketplace, with
 * users spending credits to obtain said assets.
 */
public class Assets implements Serializable {
    private static final long serialVersionUID = 6118712080445043041L;

    private int assetID;
    private String assetName;
    private int quantity;
    private int unitID;

    /**
     * Constructor for an Assets object
     */
    public Assets() {
    }

    /**
     * Constructor for an Assets object
     * @param assetID The asset's ID
     * @param assetName The asset's name
     * @param quantity The quantity of the asset
     */
    public Assets(int assetID, String assetName, int quantity) {
        this.assetID = assetID;
        this.assetName = assetName;
        this.quantity = quantity;
    }

    /**
     * Constructor for an Assets object
     * @param assetID The asset's ID
     * @param assetName The asset's name
     * @param quantity The asset's quantity
     * @param unitID The unit that owns this instance of the asset
     */
    public Assets(int assetID, String assetName, int quantity, int unitID) {
        this.assetID = assetID;
        this.assetName = assetName;
        this.quantity = quantity;
        this.unitID = unitID;
    }

    /**
     * Get the asset ID
     * @return The asset's ID
     */
    public int getAssetID() {
        return assetID;
    }

    /**
     * Get the asset's name
     * @return The asset's name
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * Get the quantity of the asset
     * @return The quantity of the asset
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get the ID of the unit that owns the asset
     * @return The unit's ID
     */
    public int getUnitID() {
        return unitID;
    }

    /**
     * Set the asset's ID
     * @param assetID The ID to be assigned to the asset
     */
    public void setAssetID(int assetID) throws AssetsException {
        if(assetID >= 0) {
            this.assetID = assetID;
        } else {
            throw new AssetsException("The Asset ID cannot be a negative integer");
        }
    }

    /**
     * Set the asset's name
     * @param assetName The name of the asset
     */
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    /**
     * Set the quantity of the asset
     * @param quantity The quantity
     */
    public void setQuantity(int quantity) throws AssetsException {
        if(quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new AssetsException("The quantity cannot be a negative integer");
        }
    }

    /**
     * Set the ID of the unit that owns the asset
     * @param unitID The unit's ID
     */
    public void setUnitID(int unitID) throws AssetsException {
        if(unitID >= 0) {
            this.unitID = unitID;
        } else {
            throw new AssetsException("The unit ID cannot be a negative integer");
        }
    }
}
