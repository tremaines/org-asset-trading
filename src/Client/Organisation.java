package Client;

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
    // Type of asset or resource owned
    private String assetType;
    // Amount of the asset or resource owned
    private int assetAmount;

    /**
     * Organisation constructor that initialises an organisational unit
     *
     * @param organisationName name of the organisation
     * @param credits number of electronic credits
     * @param assetType type of asset
     * @param assetAmount quantity of asset type
     */
    public Organisation(String organisationName, int credits, String assetType, int assetAmount) {
        this.organisationName = organisationName;
        this.credits = credits;
        this.assetType = assetType;
        this.assetAmount = assetAmount;
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
