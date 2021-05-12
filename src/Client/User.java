package Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Users are accounts which have been authorised by a particular organisation unit
 * to engage on the electronic trading marketplace.
 */
public class User {

    // Username to login to the marketplace
    private String username;
    // List of usernames to check against to prevent duplicates
    private List<String> users = new ArrayList<>();
    // Hashed value of the password
    private String password;
    // Type of account (admin or non-admin)
    private boolean admin;
    // Organisation name the account is registered to
    private String organisationName;
    // List containing all users added
    private List<User> userList = new ArrayList<>();
    // Collection of Organisation instances
    private Organisation org;
    // Notification status on a sell listing
    private boolean notifySell;
    // Notification status
    private boolean notifyBuy;

    /**
     * Creates an instance of users
     *
     * @param organisation Instance of the Organisation class
     */
    public User(Organisation organisation) {
        org = new Organisation();
        org = organisation;
    }

    /**
     * User constructor that creates an authorised account from an organisation
     *
     * @param username Name of the user's account
     * @param password Password to log in to the account
     * @param admin Admin status (true/false)
     * @param organisationName Name of the organisation the user is associated with
     */
    public User (String username, String password, boolean admin, String organisationName) {
        users.add(username);
        this.username = username;

        // Hashed password
        this.password = hashPassword(password);
        this.admin = admin;
        this.organisationName = organisationName;

        // Notification status default value is false
        notifySell = false;
        notifyBuy = false;
    }

    /**
     * Creates a new instance of a user and adds the user into the list of existing users
     *
     * @param username Username of the account
     * @param password Password to access the account
     * @param admin Admin status (true/false)
     * @param organisationName Name of the organisation the user is associated with
     */
    public void createUser(String username, String password, boolean admin, String organisationName) throws UserException {
        if(users.contains(username)) {
            throw new UserException("This username is already taken! Please try another.");
        } else {
            // List that monitors list of taken names to prevent duplicates
            users.add(username);

            // Creates the new user account if the chosen username is available
            User newUser = new User(username, password, admin, organisationName);

            // Adds a new user to the list of all users
            userList.add(newUser);
        }
    }

    /**
     * Gets the username of the account
     *
     * @return account username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Hashes the plain-text password value
     *
     * @param password Password for a user account
     * @return Hashed string value of plain-text password
     */
    public String hashPassword(String password) {
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
     * Gets the the organisation name the account is registered to
     *
     * @return Organisation name associated with the user
     */
    public String getOrganisationName(String username) {
        return getUser(username).getOrganisationName();
    }

    /**
     * Gets the the organisation name associated with the instance of the user
     *
     * @return Organisation name of the user
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * Checks whether a the username and password details entered match those of the actual user
     * account
     *
     * @param username Name of the user account
     * @param password Password for the user account
     * @return True or false
     */
    public boolean loginSuccessful(String username, String password) {
        if(users.contains(username)) {
            User userToLogin = getUser(username);

            String hashedPassword = hashPassword(password);

            if(hashedPassword.equals(userToLogin.getHashedPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the list of usernames to verify whether the username entered exists within the list
     *
     * @param username Name of the user account
     * @return True or false
     */
    public boolean userExists(String username) {
        if(users.contains(username)) {
            User userToLogin = getUser(username);
            return true;
        }
        return false;
    }

    /**
     * Gets the User object based on a matching username
     *
     * @return User object of the user account
     */
    public User getUser(String username) {
        User userObject = new User(org);

        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().equals(username)) {
                userObject = userList.get(i);
            }
        }
        return userObject;
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
     * @param status True if user's order has been completed/partially completed
     */
    public void setNotificationStatus(String type, boolean status) {
        if(type == "Buy") {
            notifyBuy = status;
        } else if(type == "Sell") {
            notifySell = status;
        }
    }
}
