package Server;

import Client.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * A wrapper class for accessing the users table
 */
public class UserDBSource {
    // SELECT statements
    private static final String GET_PASSWORD = "SELECT password FROM users WHERE user_name=?";
    private static final String CHECK_USERNAME = "SELECT * FROM users WHERE user_name=?";
    private static final String GET_USER = "SELECT * FROM users WHERE user_name=?";

    // Prepared statements
    private PreparedStatement getPassword;
    private PreparedStatement checkUserName;
    private PreparedStatement getUser;

    private Connection connection;

    /***
     * Constructor
     *
     * @param connection Takes a connection to the trading_platform database
     */
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

    /***
     * Retrieves a user's password as a string (hashed)
     *
     * @param username The unique username of the user
     * @return The user's password (if they exist)
     */
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

    /***
     * Check if the username provided exists in the database (useful for login)
     *
     * @param username The username as a string
     * @return True if the user exists, false otherwise
     */
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

    /***
     * Creates an instance of the user class based on a provided username
     *
     * @param username The username as a string
     * @return An instance of the User class (if the username exists)
     */
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
            user.setUnit(rs.getInt("unit"));
            user.setPassword(rs.getString("password"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        }

        return user;
    }

    /***
     * Closes the connection to the database
     */
    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException sqle){
            System.err.println(sqle);
        }
    }
}
