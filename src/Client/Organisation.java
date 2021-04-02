package Client;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Organisation constructor that initialises an organisational unit
     *
     * @param organisationName name of the organisation
     * @param credits number of electronic credits
     * @param assetType type of asset
     * @param assetAmount quantity of asset type
     */
    public Organisation(String organisationName, int credits, List<String> assetType, List<Integer> assetAmount) {
        this.organisationName = organisationName;
        this.credits = credits;
        this.assetList = assetType;
        this.amount = assetAmount;
    }

    public Organisation(String organisationName) {
        this.organisationName = organisationName;
    }

    /**
     * Gets the electronic credits available to an organisation
     *
     * @return credit amount
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the electronic credits available to an organisation
     *
     * @param amount credit amount
     */
    public void setCredits(int amount) {
        this.credits = amount;
    }
}
