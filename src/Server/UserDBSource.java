package Server;

import Client.User;
import Client.UserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * A wrapper class for accessing the users table
 */
public class UserDBSource implements UserDB {
    // SELECT statements
    private static final String GET_PASSWORD = "SELECT password FROM users WHERE user_name=?";
    private static final String CHECK_USERNAME = "SELECT * FROM users WHERE user_name=?";
    private static final String GET_USER = "SELECT * FROM users WHERE user_name=?";
    private static final String ADD = "INSERT INTO users (user_name, first_name, last_name, email, admin_status, " +
            "unit, password) VALUES(?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ?, admin_status = ?, " +
            "unit = ?, password = ?, notify_buy = ?, notify_sell = ? WHERE user_name = ?;";

    // Prepared statements
    private PreparedStatement getPassword;
    private PreparedStatement checkUserName;
    private PreparedStatement getUser;
    private PreparedStatement addUser;
    private PreparedStatement update;

    private Connection connection;

    /***
     * Constructor
     */
    public UserDBSource() {
        this.connection = DBConnection.getConnection();
        try{
            getPassword = connection.prepareStatement(GET_PASSWORD);
            checkUserName = connection.prepareStatement(CHECK_USERNAME);
            getUser = connection.prepareStatement(GET_USER);
            addUser = connection.prepareStatement(ADD);
            update = connection.prepareStatement(UPDATE);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
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
            user.setNotificationStatus("Buy", rs.getBoolean("notify_buy"));
            user.setNotificationStatus("Sell", rs.getBoolean("notify_sell"));
        } catch(SQLException sqle){
            System.err.println(sqle);
        } catch (UserException usere) {
            System.err.println(usere);
        }

        return user;
    }

    /**
     * Add a new user to the database
     * @param user The user object to be added
     */
    public void addUser(User user) {
        try {
            addUser.setString(1, user.getUsername());
            addUser.setString(2, user.getFirstName());
            addUser.setString(3, user.getLastName());
            addUser.setString(4, user.getEmail());
            addUser.setBoolean(5, user.getAdminStatus());
            addUser.setInt(6, user.getUnit());
            addUser.setString(7, user.getHashedPassword());
            addUser.execute();
        } catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Update the details of a user stored in the database
     * @param user The user to be updated
     */
    public void update(User user) {
        try{
            update.setString(1, user.getFirstName());
            update.setString(2, user.getLastName());
            update.setString(3, user.getEmail());
            update.setBoolean(4, user.getAdminStatus());
            update.setInt(5, user.getUnit());
            update.setString(6, user.getHashedPassword());
            update.setBoolean(7, user.getBuyNotificationStatus());
            update.setBoolean(8, user.getSellNotificationStatus());
            update.setString(9, user.getUsername());

            update.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
