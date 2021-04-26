package Server;

import Client.Units;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * A wrapper class for accessing the units table
 */
public class UnitDBSource {
    // SELECT statements
    private static final String GET_NAME = "SELECT * FROM units WHERE unit_id=?";
    private static final String GET_UNIT = "SELECT * FROM units WHERE unit_id=?";

    // Prepared statements
    private PreparedStatement getName;
    private PreparedStatement getUnit;

    private Connection connection;

    /***
     * Constructor
     *
     * @param connection A connection to the trading_platform database
     */
    public UnitDBSource(Connection connection) {
        this.connection = connection;
        try{
            getName = connection.prepareStatement(GET_NAME);
            getUnit = connection.prepareStatement(GET_UNIT);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Retrieves a unit's name based on its ID from the database
     *
     * @param id The unit's ID as a string
     * @return The name of the unit as a String
     */
    public String unitName(String id) {
        String name = "";

        ResultSet rs = null;

        try {
            getName.setString(1, id);
            rs = getName.executeQuery();
            rs.next();
            name = rs.getString("unit_name");
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }

        return name;
    }
    /***
     * Retrieves the details of a unit based on its id from the database and creates an instance of that class
     * @param id The unit id as a String
     * @return An instance of the Units class
     */
    public Units getUnit(String id) {
        Units unit = new Units();
        ResultSet rs = null;

        try {
            getUnit.setString(1, id);
            rs = getUnit.executeQuery();
            rs.next();

            unit.setUnitID(rs.getString("unit_id"));
            unit.setUnitName(rs.getString("unit_name"));
            unit.setCredits(rs.getInt("credits"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return unit;
    }
}
