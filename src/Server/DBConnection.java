package Server;

import Client.Units;
import Client.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/***
 * Creates a connection for the server to the database.
 *
 * Much of this code can be attributed to CAB302 Prac 7
 */
public class DBConnection {

    // The singleton instance
    private static Connection connection = null;

    // CREATE statement for database
    private static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS trading_platform;";

    // CREATE statement for tables
    private static final String CREATE_UNITS =
            "CREATE TABLE IF NOT EXISTS units (" +
                    "unit_id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "unit_name VARCHAR(70) NOT NULL," +
                    "credits INT NOT NULL," +
                    "PRIMARY KEY (unit_id)" +
                    ");";
    private static final String CREATE_USERS =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "user_name VARCHAR(30) NOT NULL UNIQUE," +
                    "first_name VARCHAR(30)," +
                    "last_name VARCHAR(30)," +
                    "email VARCHAR(60)," +
                    "admin_status BOOLEAN NOT NULL," +
                    "unit INT NOT NULL," +
                    "password VARCHAR(260) NOT NULL," +
                    "notify_buy BOOLEAN NOT NULL DEFAULT FALSE," +
                    "notify_sell BOOLEAN NOT NULL DEFAULT FALSE," +
                    "PRIMARY KEY (user_name)," +
                    "FOREIGN KEY (unit) REFERENCES units(unit_id)" +
                    ");";
    private static final String CREATE_ASSETS =
            "CREATE TABLE IF NOT EXISTS assets (" +
                    "asset_id INT NOT NULL AUTO_INCREMENT UNIQUE," +
                    "asset_name VARCHAR(100) NOT NULL," +
                    "PRIMARY KEY (asset_id)" +
                    ");";
    private static final String CREATE_TRADES =
            "CREATE TABLE IF NOT EXISTS trades (" +
                    "trade_id INT NOT NULL," +
                    "type ENUM('buy', 'sell') NOT NULL," +
                    "user VARCHAR(30) NOT NULL," +
                    "asset INT NOT NULL," +
                    "quantity INT NOT NULL," +
                    "price INT NOT NULL," +
                    "date DATETIME NOT NULL," +
                    "PRIMARY KEY (trade_id)," +
                    "FOREIGN KEY (user) REFERENCES users(user_name)," +
                    "FOREIGN KEY (asset) REFERENCES assets(asset_id)" +
                    ");";
    private static final String CREATE_TRADES_HX =
            "CREATE TABLE IF NOT EXISTS trade_history (" +
                    "trade_id INT NOT NULL AUTO_INCREMENT," +
                    "status ENUM('complete', 'cancelled') NOT NULL," +
                    "asset INT NOT NULL," +
                    "qty INT NOT NULL," +
                    "credits INT NOT NULL," +
                    "date DATETIME," +
                    "seller VARCHAR(30)," +
                    "buyer VARCHAR(30)," +
                    "PRIMARY KEY (trade_id)," +
                    "FOREIGN KEY (seller) REFERENCES users(user_name)," +
                    "FOREIGN KEY (buyer) REFERENCES users(user_name)" +
                    ");";
    private static final String CREATE_ASSETS_OWNED =
            "CREATE TABLE IF NOT EXISTS assets_owned (" +
                    "asset_id INT NOT NULL," +
                    "unit INT NOT NULL," +
                    "quantity INT NOT NULL," +
                    "PRIMARY KEY (asset_id, unit)," +
                    "FOREIGN KEY (asset_id) REFERENCES assets(asset_id)," +
                    "FOREIGN KEY (unit) REFERENCES units(unit_id)" +
                    ");";

    // Store statements in an array
    private static final String[] createTables = {CREATE_UNITS, CREATE_USERS, CREATE_ASSETS, CREATE_TRADES,
            CREATE_TRADES_HX, CREATE_ASSETS_OWNED};

    // CREATE statements for first uni (IT Admin) and user (root)
    private static final String ADD_FIRST_UNIT = "INSERT INTO units(unit_name, credits) VALUES (?, ?);";
    private static final String ADD_FIRST_ADMIN = "INSERT INTO users(user_name, admin_status, unit, password) " +
            "VALUES (?, ?, ?, ?);";
    private static final Units unit = new Units("IT Administration", 10000);
    private static final User user = new User("admin", true, 1, "password");

    /***
     * Constructor initialises connection, creates database and tables if necessary
     */
    private DBConnection() {
        Properties props = new Properties();
        FileInputStream details = null;

        try {
            // Read in details for database connection
            details = new FileInputStream("./src/Server/dbserver.props");
            props.load(details);
            details.close();

            String url = props.getProperty("jdbc.url");
            String user = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            // Connect to MariaDB IP
            connection = DriverManager.getConnection(url + "/", user, password);
            // Create database if needed
            createDatabase();
            // Change connection to the database
            connection.setCatalog("trading_platform");
            // Create tables if needed
            createTables();
            // Insert first unit and user if needed
            insertUnitAndUser();


        } catch (SQLException | FileNotFoundException sqle) {
            System.err.println(sqle);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /***
     * Helper method to execute the CREATE DATABASE statement
     */
    private void createDatabase() {
        try {
            Statement stmnt = connection.createStatement();
            stmnt.execute(CREATE_DATABASE);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Helper method to execute the CREATE TABLE statements
     */
    private void createTables() {
        try {
            Statement stmnt = connection.createStatement();
            for(String create : createTables) {
                stmnt.execute(create);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Helper method to execute the INSERT statements for the initial unit and user
     */
    private void insertUnitAndUser() {
        if (!checkTable("units")) {
            addFirstUnit(unit);
        }
        if (!checkTable("users")) {
            addFirstUser(user);
        }
    }

    /***
     * Helper method to create an initial unit and user
     * @param org The first organisation to be added (IT Admin)
     */
    private void addFirstUnit(Units org) {
        try {
            PreparedStatement addUnit = connection.prepareStatement(ADD_FIRST_UNIT);

            // Add unit details
            addUnit.setString(1, org.getUnitName());
            addUnit.setInt(2, org.getCredits());
            addUnit.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Helper method to create an initial unit and user
     * @param root The first user (root)
     */
    private void addFirstUser(User root) {
        try {
            PreparedStatement addRoot = connection.prepareStatement(ADD_FIRST_ADMIN);

            //Add user details
            addRoot.setString(1, root.getUsername());
            addRoot.setBoolean(2, root.getAdminStatus());
            addRoot.setInt(3, root.getUnit());
            addRoot.setString(4, root.getHashedPassword());
            addRoot.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /***
     * Checks if there are any relations in a table
     *
     * @param table The table as a string
     * @return True if there are relations, false otherwise
     */
    private boolean checkTable(String table) {
        boolean exists = false;
        String CHECK_TABLE = "SELECT * FROM " + table + ";";

        try {
        PreparedStatement checkCount = connection.prepareStatement(CHECK_TABLE);
        ResultSet rs = null;
        rs = checkCount.executeQuery();
        exists =  rs.next();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
        return exists;
    }

    /***
     * Public method to create a connection to the database
     * @return A connection to the database
     */
    public static Connection getConnection() {
        if (connection  == null) {
            new DBConnection();
        }
        return connection;
    }
}
