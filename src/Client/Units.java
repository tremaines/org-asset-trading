package Client;

/**
 * Resources and assets are shared across organisations. Organisations are also given budgets
 * of a certain number of electronic credits. These credits can be used to buy access to
 * resources from other organisational units.
 */
public class Units {

    // Name of the organisation
    private String unitName;
    private String unitID;
    // Electronic credits available to the organisation
    private int credits;

    public Units() {
    }

    /**
     * Units constructor that initialises an organisational unit
     *
     * @param unitName Name of the organisation
     * @param credits Number of electronic credits
     */
    public Units(String unitID, String unitName, int credits) {
        this.unitName = unitName;
        this.credits = credits;
        this.unitID = unitID;
    }

    /**
     * Gets the name of the current organisation object
     *
     * @return Name of the organisation
     */
    public String getUnitName() {
        return unitName;
    }

    public String getUnitID() {
        return unitID;
    }

    /**
     * Gets the electronic credits available to an organisation
     *
     * @return Credit amount currently owned by an organisation
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the electronic credits available to an organisation
     *
     * @param amount New credit amount to be owned by an organisation
     */
    public void setCredits(int amount) {
        this.credits = amount;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
    //    /**
//     * Gets all assets currently owned by the organisation
//     *
//     * @return The list of assets owned
//     */
//    public List<String> getAssets() {
//        return assetList;
//    }
//
//    /**
//     * Gets quantity of assets owned matched by index value of asset list
//     *
//     * @return List containing quantities of assets
//     */
//    public List<Integer> getAmounts() {
//        return amount;
//    }
//
//    /**
//     * Maps both the lists of assets owned and the amount of each asset owned into a treemap
//     * sorted by key
//     *
//     * @return Assets owned and quantity of each asset
//     */
//    public TreeMap<String, Integer> getAssetsAndAmounts() {
//        for(int i = 0; i < assetList.size(); i++) {
//            assetMap.put(assetList.get(i), amount.get(i));
//        }
//        return assetMap;
//    }
//
//    /**
//     * Adds an asset to the list of the organisation's assets
//     *
//     * @param assetName Name of the asset to be added
//     * @param assetQuantity Quantity of the asset to be added
//     */
//    public void addAssets(String assetName, int assetQuantity) {
//        boolean ownsAsset = assetList.contains(assetName);
//        int index;
//
//        // If the organisation already owns at least 1 unit of this asset
//        if(ownsAsset) {
//            index = assetList.indexOf(assetName);
//            int currentQuantity = amount.get(index);
//            amount.set(index, currentQuantity + assetQuantity);
//        } else {
//            assetList.add(assetName);
//            amount.add(assetQuantity);
//        }
//    }
//
//    /**
//     * Decreases the asset quantity of an organisation's asset as specified. If the new asset
//     * quantity is zero, the asset is removed from the organisation's list of assets.
//     *
//     * @param assetName Name of the asset to be removed/decreased
//     * @param assetQuantity Quantity to be decreased by
//     */
//    public void removeAssets(String assetName, int assetQuantity) {
//        // Index of the asset in the list, the index of the amount list will be the same
//        int index = assetList.indexOf(assetName);
//
//        // Gets current quantity of asset
//        int quantity = amount.get(index);
//
//        // Decreases the asset amount by the value to be removed
//        quantity = quantity - assetQuantity;
//
//        // Remove the asset if it's quantity is 0, otherwise decrease the asset amount by the
//        // value to be removed
//        if(quantity == 0) {
//            assetList.remove(index);
//            amount.remove(index);
//        } else {
//            amount.set(index, quantity);
//        }
//    }
}
