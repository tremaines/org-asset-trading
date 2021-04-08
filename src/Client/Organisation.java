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

    // Name of the organisation
    private String organisationName;
    // Electronic credits available to the organisation
    private int credits;
    // List of asset or resource owned
    private List<String> assetList;
    // List of amount of each asset owned
    private List<Integer> amount;
    // Treemap of asset and asset amount
    TreeMap<String, Integer> assetMap = new TreeMap<>();

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
        Assets asset = new Assets();
        assetList = new ArrayList<>();
        amount= new ArrayList<>();

        // Throws an exception if unapproved assets are found in the assetList and clears both
        // assetType and assetAmount lists
        try{
            if(!asset.checkAssetList(assetType)) {
                assetType.clear();
                assetAmount.clear();
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("An unapproved asset is contained within this organisation!");
            return;
        }

        this.organisationName = organisationName;
        this.credits = credits;
        this.assetList = assetType;
        this.amount = assetAmount;
    }

    /**
     * Constructs an organisation with the given organisation name
     *
     * @param organisationName The name of the organisation
     */
    public Organisation(String organisationName) {
        this.organisationName = organisationName;
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
     * @param amount Credit amount to be owned by an organisation
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
}
