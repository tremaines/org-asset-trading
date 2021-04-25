package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Resources and assets are shared across organisations. Organisations are also given budgets
 * of a certain number of electronic credits. These credits can be used to buy access to
 * resources from other organisational units.
 */
public class Organisation {

    // List containing all organisations added
    private List<Organisation> organisationalList = new ArrayList<>();
    // Name of the organisation
    private String organisationName;
    // Electronic credits available to the organisation
    private int credits;
    // List of asset or resource owned
    private List<String> assetList = new ArrayList<>();
    // List of amount of each asset owned
    private List<Integer> amount = new ArrayList<>();
    // Treemap of asset and asset amount
    private TreeMap<String, Integer> assetMap = new TreeMap<>();

    /**
     * Creates an instance of the Organisation
     */
    public Organisation() {
    }

    /**
     * Organisation constructor that initialises an organisational unit
     *
     * @param organisationName Name of the organisation
     * @param credits Number of electronic credits
     * @param assetType The type of the asset
     * @param assetAmount Quantity of the asset type
     */
    public Organisation(String organisationName, int credits, List<String> assetType,
                        List<Integer> assetAmount) {
        this.organisationName = organisationName;
        this.credits = credits;
        this.assetList = assetType;
        this.amount = assetAmount;
    }

    /**
     * Adds a new organisation into the list of organisations and gives the organisation access
     * to the platform
     *
     * @param organisationName Name of the organisation
     * @param credits Number of electronic credits
     * @param assetList The type of the asset
     * @param amount Amount of the asset type
     */
    public void createOrganisation(String organisationName, int credits, List<String> assetList,
                                   List<Integer> amount) {
        Organisation org = new Organisation(organisationName, credits, assetList,
                amount);
        // Adds new organisation to the list of all organisations
        organisationalList.add(org);
    }

    /**
     * Gets a list of all organisation objects currently added onto the platform
     *
     * @return List of all organisation objects
     */
    public List<Organisation> getOrganisationList() {
        return organisationalList;
    }

    /**
     * Gets the Organisation object based on a matching Organisation name
     *
     * @return Organisation object from the list of organisations
     */
    public Organisation getOrganisation(String organisationName) {
        Organisation orgObject = new Organisation();
        for(Organisation org: organisationalList) {
            if(org.getOrganisationName() == organisationName) {
                orgObject = org;
            }
        }
        return orgObject;
    }

    /**
     * Gets the Organisation object based on a matching Organisation name
     *
     * @param organisationList List of all Organisations to get Organisation object from
     * @param organisationName Name of the Organisation
     * @return Organisation object from the list of organisations
     */
    public Organisation getOrganisation(List<Organisation> organisationList, String organisationName) {
        Organisation orgObject = new Organisation();
        for(Organisation org: organisationList) {
            if(org.getOrganisationName() == organisationName) {
                orgObject = org;
            }
        }
        return orgObject;
    }

    /**
     * Gets the name of the current organisation object
     *
     * @return Name of the organisation
     */
    public String getOrganisationName() {
        return organisationName;
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

    /**
     * Gets all assets currently owned by the organisation
     *
     * @return The list of assets owned
     */
    public List<String> getAssets() {
        return assetList;
    }

    /**
     * Gets quantity of assets owned matched by index value of asset list
     *
     * @return List containing quantities of assets
     */
    public List<Integer> getAmounts() {
        return amount;
    }

    /**
     * Maps both the lists of assets owned and the amount of each asset owned into a treemap
     * sorted by key
     *
     * @return Assets owned and quantity of each asset
     */
    public TreeMap<String, Integer> getAssetsAndAmounts() {
        for(int i = 0; i < assetList.size(); i++) {
            assetMap.put(assetList.get(i), amount.get(i));
        }
        return assetMap;
    }

    /**
     * Adds an asset to the list of the organisation's assets
     *
     * @param assetName Name of the asset to be added
     * @param assetQuantity Quantity of the asset to be added
     */
    public void addAssets(String assetName, int assetQuantity) {
        boolean ownsAsset = assetList.contains(assetName);
        int index;

        // If the organisation already owns at least 1 unit of this asset
        if(ownsAsset) {
            index = assetList.indexOf(assetName);
            int currentQuantity = amount.get(index);
            amount.set(index, currentQuantity + assetQuantity);
        } else {
            assetList.add(assetName);
            amount.add(assetQuantity);
        }
    }

    /**
     * Decreases the asset quantity of an organisation's asset as specified. If the new asset
     * quantity is zero, the asset is removed from the organisation's list of assets.
     *
     * @param assetName Name of the asset to be removed/decreased
     * @param assetQuantity Quantity to be decreased by
     */
    public void removeAssets(String assetName, int assetQuantity) {
        // Index of the asset in the list, the index of the amount list will be the same
        int index = assetList.indexOf(assetName);

        // Gets current quantity of asset
        int quantity = amount.get(index);

        // Decreases the asset amount by the value to be removed
        quantity = quantity - assetQuantity;

        // Remove the asset if it's quantity is 0, otherwise decrease the asset amount by the
        // value to be removed
        if(quantity == 0) {
            assetList.remove(index);
            amount.remove(index);
        } else {
            amount.set(index, quantity);
        }
    }
}
