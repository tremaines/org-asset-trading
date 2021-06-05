package Client;

import java.io.Serializable;

/**
 * Resources and assets are shared across organisations. Organisations are also given budgets
 * of a certain number of electronic credits. These credits can be used to buy access to
 * resources from other organisational units.
 */
public class Units implements Serializable {
    private static final long serialVersionUID = -6210314430585695691L;

    private String unitName;
    private int unitID;
    private int credits;

    /**
     * Constructor
     */
    public Units() { }

    /**
     * Constructor for a Units object
     *
     * @param unitName The name of the unit
     * @param credits The number of credits the unit has
     */
    public Units(String unitName, int credits) {
        this.unitName = unitName;
        this.credits = credits;
    }

    /**
     * Constructor for a Units object
     *
     * @param unitID The unit's ID
     * @param unitName The name of the unit
     * @param credits The number of credits the unit has
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
    public void setCredits(int amount) throws UnitsException {
        if(amount >= 0) {
            this.credits = amount;
        } else {
            throw new UnitsException("Credits cannot be negative");
        }
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
    public void setUnitID(int unitID) throws UnitsException {
        if (unitID < 0) {
            throw new UnitsException("Unit ID cannot be negative");
        } else {
            this.unitID = unitID;
        }
    }
}
