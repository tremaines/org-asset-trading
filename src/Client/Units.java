package Client;

/**
 * Resources and assets are shared across organisations. Organisations are also given budgets
 * of a certain number of electronic credits. These credits can be used to buy access to
 * resources from other organisational units.
 */
public class Units {

    // Name of the organisation
    private String unitName;
    private int unitID;
    // Electronic credits available to the organisation
    private int credits;

    public Units() {
    }

    public Units(String unitName, int credits) {
        this.unitName = unitName;
        this.credits = credits;
    }

    /**
     * Units constructor that initialises an organisational unit
     *
     * @param unitName Name of the organisation
     * @param credits Number of electronic credits
     */
    public Units(int unitID, String unitName, int credits) {
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

    public int getUnitID() {
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

    /**
     * Setter for the unit's name
     * @param unitName
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * Setter for the unit ID
     * @param unitID
     */
    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }
}
