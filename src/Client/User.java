package Client;

import java.io.Serializable;

/**
 * Users are accounts which have been authorised by a particular organisation unit
 * to engage on the electronic trading marketplace.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7384848292353565937L;

    private String firstName;
    private String lastName;
    private String email;
    // Username to login to the marketplace
    private String username;
    // Hashed value of the password
    private String password;
    // Type of account (admin or non-admin)
    private boolean admin;
    // Units name the account is registered to
    private int unit;
    // Notification status on a sell listing
    private boolean notifySell;
    // Notification status
    private boolean notifyBuy;

    public User() { }

    /**
     * User constructor that creates an authorised account from an organisation
     *
     * @param username Name of the user's account
     * @param password Password to log in to the account
     * @param admin Admin status (true/false)
     * @param unit Name of the unit the user is associated with
     */
    public User (String firstName, String lastName, String email, String username,
                 String password, boolean admin, int unit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        // Hashed password
        this.password = hashPassword(password);
        this.admin = admin;
        this.unit = unit;
        // Notification status default value is false
        notifySell = false;
        notifyBuy = false;
    }
    public User (String username, boolean admin, int unit, String password) {
        this.username = username;
        this.admin = admin;
        this.unit = unit;
        this.password = hashPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() { return email; }

    /**
     * Gets the username of the account
     *
     * @return account username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Hashes the plain-text password value - public so we can use it to has the user input
     * at login time
     *
     * @param password Password for a user account
     * @return Hashed string value of plain-text password
     */
    public static String hashPassword(String password) {
        String hashedPassword = (password.hashCode() * 2.334) + "";
        return hashedPassword;
    }

    /**
     * Hashes a plain-text password
     *
     * @return hashed password
     */
    public String getHashedPassword() {
        return password;
    }

    /**
     * Changes user password
     */
    public void changePassword(String newPassword) {
        password = hashPassword(newPassword);
    }

    /**
     * Gets the type of the account (true if admin, false if non-admin)
     *
     * @return account type
     */
    public boolean getAdminStatus() {
        return admin;
    }

    /**
     * Gets the the organisation name associated with the instance of the user
     *
     * @return Units name of the user
     */
    public int getUnit() { return unit; }

    /**
     * Setter for user's first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for user's last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for user's email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for username (login id)
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for user's admin status
     * @param admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Setter for the user's unit
     * @param unit
     */
    public void setUnit(int unit) {
        this.unit = unit;
    }

    /**
     * Checks if a user needs to be notified of a completed/partially buy listing
     *
     * @return True or false if a user needs to be notified
     */
    public boolean getBuyNotificationStatus() {
        return notifyBuy;
    }

    /**
     * Checks if a user needs to be notified of a completed/partially sell listing
     *
     * @return True or false if a user needs to be notified
     */
    public boolean getSellNotificationStatus() {
        return notifySell;
    }

    /**
     * Sets the notification status
     *
     * @param status True or false if a user needs to be notified
     */
    public void setNotificationStatus(String type, boolean status) {
        if(type == "Buy") {
            notifyBuy = status;
        } else if(type == "Sell") {
            notifySell = status;
        }
    }
}
