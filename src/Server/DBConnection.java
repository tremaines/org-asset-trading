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

    // The single instance
    private static Connection connection = null;

    // CREATE statement for database
    private static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS trading_platform;";

    // CREATE statement for tables
    private static final String CREATE_UNITS =
            "CREATE TABLE IF NOT EXISTS units (" +
                    "unit_id VARCHAR(10) NOT NULL UNIQUE," +
                    "unit_name VARCHAR(70) NOT NULL," +
                    "credits INT NOT NULL," +
                    "PRIMARY KEY (unit_id)" +
                    ");";
    private static final String CREATE_USERS =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "user_name VARCHAR(30) NOT NULL UNIQUE," +
                    "first_name VARCHAR(30) NOT NULL," +
                    "last_name VARCHAR(30) NOT NULL," +
                    "email VARCHAR(60) NOT NULL," +
                    "admin_status BOOLEAN NOT NULL," +
                    "unit VARCHAR(10) NOT NULL," +
                    "password VARCHAR(260) NOT NULL," +
                    "PRIMARY KEY (user_name)," +
                    "FOREIGN KEY (unit) REFERENCES units(unit_id)" +
                    ");";
    private static final String CREATE_ASSETS_PRO =
            "CREATE TABLE IF NOT EXISTS assets_produced (" +
                    "asset_id VARCHAR(10) NOT NULL UNIQUE," +
                    "asset_name VARCHAR(100) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "unit VARCHAR(10) NOT NULL," +
                    "PRIMARY KEY (asset_id)," +
                    "FOREIGN KEY (unit) REFERENCES units(unit_id)" +
                    ");";
    private static final String CREATE_TRADES =
            "CREATE TABLE IF NOT EXISTS trades (" +
                    "trade_id INT NOT NULL AUTO_INCREMENT," +
                    "type ENUM('buy', 'sell') NOT NULL," +
                    "user VARCHAR(30) NOT NULL," +
                    "asset VARCHAR(10) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "price INT NOT NULL," +
                    "date DATETIME NOT NULL," +
                    "PRIMARY KEY (trade_id)," +
                    "FOREIGN KEY (user) REFERENCES users(user_name)," +
                    "FOREIGN KEY (asset) REFERENCES assets_produced(asset_id)" +
                    ");";
    private static final String CREATE_TRADES_HX =
            "CREATE TABLE IF NOT EXISTS trade_history (" +
                    "trade_id INT NOT NULL AUTO_INCREMENT," +
                    "status ENUM('complete', 'cancelled') NOT NULL," +
                    "asset VARCHAR(10) NOT NULL," +
                    "qty INT NOT NULL," +
                    "date DATETIME," +
                    "seller VARCHAR(30) NOT NULL," +
                    "buyer VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (trade_id)," +
                    "FOREIGN KEY (seller) REFERENCES users(user_name)," +
                    "FOREIGN KEY (buyer) REFERENCES users(user_name)" +
                    ");";
    private static final String CREATE_ASSETS_PUR =
            "CREATE TABLE IF NOT EXISTS assets_purchased (" +
                    "asset_id VARCHAR(10) NOT NULL," +
                    "unit VARCHAR(10) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "credits INT NOT NULL," +
                    "PRIMARY KEY (asset_id, unit)," +
                    "FOREIGN KEY (asset_id) REFERENCES assets_produced(asset_id)," +
                    "FOREIGN KEY (unit) REFERENCES units(unit_id)" +
                    ");";

    // Store statements in an array
    private static final String[] createTables = {CREATE_UNITS, CREATE_USERS, CREATE_ASSETS_PRO, CREATE_TRADES,
            CREATE_TRADES_HX, CREATE_ASSETS_PUR};

    private static final String ADD_FIRST_UNIT = "INSERT INTO units(unit_id, unit_name, credits) VALUES (?, ?, ?)";
    private static final String ADD_FIRST_ADMIN = "INSERT INTO users(user_name, first_name, last_name, email, " +
            "admin_status, unit, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /***
     * Constructor initialises connection, creates database and tables if necessary
     * @param path
     */
    private DBConnection(String path) {
        Properties props = new Properties();
        FileInputStream details = null;

        try {
            // Read in details for database connection
            details = new FileInputStream(path);
            props.load(details);
            details.close();

            String url = props.getProperty("jdbc.url");
            String schema = props.getProperty("jdbc.schema");
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

            Units it = new Units("itadmn","IT Admin Team", 12000);
            User root = new User("Homer", "Simpson", "chunkylover53@aol.com",
                    user, password, true, "itadmn");
            addRoot(it, root);

        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void createDatabase() {
        try {
            Statement stmnt = connection.createStatement();
            stmnt.execute(CREATE_DATABASE);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

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

    private void addRoot(Units org, User root) {
        try {
            PreparedStatement addUnit = connection.prepareStatement(ADD_FIRST_UNIT);
            PreparedStatement addRoot = connection.prepareStatement(ADD_FIRST_ADMIN);

            addUnit.setString(1, org.getUnitID());
            addUnit.setString(2, org.getUnitName());
            addUnit.setInt(3, org.getCredits());

            addUnit.execute();

            addRoot.setString(1, root.getUsername());
            addRoot.setString(2, root.getFirstName());
            addRoot.setString(3, root.getLastName());
            addRoot.setString(4, root.getEmail());
            addRoot.setBoolean(5, root.getAdminStatus());
            addRoot.setString(6, "itadmn");
            addRoot.setString(7, root.getHashedPassword());

            addRoot.execute();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public static Connection getConnection(String path) {
        if (connection  == null) {
            new DBConnection(path);
        }
        return connection;
    }

    public static void main(String[] args){
        Connection instance = DBConnection.getConnection("./src/Server/dbserver.props");

        try {
            System.out.println();
            System.out.println(connection.getCatalog());
        } catch (SQLException sqle) {
            System.out.println("Error");
        }
    }
}
