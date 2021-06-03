package Server;

import Client.Units;

/**
 * An interface for the units table
 */
public interface UnitDB {

    /**
     * Retrieve the details of a unit based on its ID
     * @param unitID The unit's ID
     * @return An instance of the Units object
     */
    Units getUnit(int unitID);

    /**
     * Retrieve the details of a unit based on its ID
     * @param name The unit's name
     * @return An instance of the Units object
     */
    Units getUnit(String name);

    /**
     * Update an existing unit
     * @param unit An instance of the Units object with updated details
     */
    void update(Units unit);

    /**
     * Add a new unit
     * @param unit An instance of the Units object, with the details to be added
     */
    void add(Units unit);

    /**
     * Get the names of all units
     * @return A string array containing all unit names
     */
    String[] getUnitNames();
}
