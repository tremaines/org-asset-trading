package Server;

import Client.Units;
import Client.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnitDBSource {
    private static final String GET_NAME = "SELECT * FROM units WHERE unit_id=?";
    private static final String CHECK_USERNAME = "SELECT COUNT(user_name) FROM users WHERE user_name=?";
    private static final String GET_UNIT = "SELECT * FROM users WHERE user_name=?";

    private Connection connection;

    private PreparedStatement getName;
    private PreparedStatement checkUserName;
    private PreparedStatement getUnit;


    public UnitDBSource(Connection connection) {
        this.connection = connection;
        try{
            getName = connection.prepareStatement(GET_NAME);
            checkUserName = connection.prepareStatement(CHECK_USERNAME);
            getUNIT = connection.prepareStatement(GET_UNIT);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public String unitName(String id) {
        String name = "";

        ResultSet rs = null;

        try {
            getName.setString(1, id;
            rs = getName.executeQuery();
            rs.next();
            name = rs.getString("unit_name");
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }

        return name;
    }

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
