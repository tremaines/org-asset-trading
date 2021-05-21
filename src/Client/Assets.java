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
     * Constructor of the asset classes that initialises the list of assets and adds the default
     * assets available onto the marketplace.
     */
    public Assets() {
    }

    public Assets(String assetName, int quantity, int unitID) {
        this.assetName = assetName;
        this.quantity = quantity;
        this.unitID = unitID;
    }

    public Assets(int assetID, String assetName, int quantity, int unitID) {
        this.assetID = assetID;
        this.assetName = assetName;
        this.quantity = quantity;
        this.unitID = unitID;
    }

    public int getAssetID() {
        return assetID;
    }

    public String getAssetName() {
        return assetName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

}
