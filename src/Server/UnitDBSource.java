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
    private static final String UPDATE = "UPDATE units SET unit_name = ?, credits = ? WHERE unit_id = ?;";

    // Prepared statements
    private PreparedStatement getName;
    private PreparedStatement getUnit;
    private PreparedStatement update;

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
            update = connection.prepareStatement(UPDATE);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Retrieves the details of a unit based on its id from the database and creates an instance of that class
     * @param id The unit id as a String
     * @return An instance of the Units class
     */
    public Units getUnit(int id) {
        Units unit = new Units();
        ResultSet rs = null;

        try {
            getUnit.setInt(1, id);
            rs = getUnit.executeQuery();
            rs.next();

            unit.setUnitID(rs.getInt("unit_id"));
            unit.setUnitName(rs.getString("unit_name"));
            unit.setCredits(rs.getInt("credits"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return unit;
    }

    public void update(Units unit) {
        try{
            update.setString(1, unit.getUnitName());
            update.setInt(2, unit.getCredits());
            update.setInt(3, unit.getUnitID());
            update.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
