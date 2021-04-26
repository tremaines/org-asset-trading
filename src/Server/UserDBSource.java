package Server;

import Client.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBSource {

    private static final String GET_PASSWORD = "SELECT password FROM users WHERE user_name=?";
    private static final String CHECK_USERNAME = "SELECT COUNT(user_name) FROM users WHERE user_name=?";
    private static final String GET_USER = "SELECT * FROM users WHERE user_name=?";

    private Connection connection;

    private PreparedStatement getPassword;
    private PreparedStatement checkUserName;
    private PreparedStatement getUser;


    public UserDBSource(Connection connection) {
        this.connection = connection;
        try{
            getPassword = connection.prepareStatement(GET_PASSWORD);
            checkUserName = connection.prepareStatement(CHECK_USERNAME);
            getUser = connection.prepareStatement(GET_USER);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public String userPassword(String username) {
        String password = "";

        ResultSet rs = null;

        try {
            getPassword.setString(1, username);
            rs = getPassword.executeQuery();
            rs.next();
            password = rs.getString("password");
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }

        return password;
    }

    public boolean checkUsername(String username) {
        boolean exists = false;
        ResultSet rs = null;

        try {
            checkUserName.setString(1, username);
            rs = checkUserName.executeQuery();
            exists =  rs.next();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
        return exists;
    }

    public User getUser(String username) {
        User user = new User();
        ResultSet rs = null;

        try {
            getUser.setString(1, username);
            rs = getUser.executeQuery();
            rs.next();

            user.setUsername(rs.getString("user_name"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setAdmin(rs.getBoolean("admin_status"));
            user.setUnit(rs.getString("unit"));
            user.setPassword(rs.getString("password"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return user;
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException sqle){
            System.err.println(sqle);
        }
    }
}
