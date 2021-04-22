package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Users are accounts which have been authorised by a particular organisation unit
 * to engage on the electronic trading marketplace.
 */
public class User extends Organisation {

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
    // Org
    private Organisation org;
    // Treemap of asset and asset amount
    private TreeMap<String, Integer> assetMap = new TreeMap<>();

    /**
     * Creates an instance of users and gets the list of all organisations from its parent
     * class (Organisation)
     *
     */
    public User() {
        super.getOrganisationList();
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
        this.password = (password.hashCode() * 2.334) + "";
        this.admin = admin;
        this.organisationName = organisationName;
    }

    /**
     * Creates a new instance of a user and adds the user into the list of existing users
     *
     * @param username Username of the account
     * @param password Password to access the account
     * @param admin Admin status (true/false)
     * @param organisationName Name of the organisation the user is associated with
     */
    public void createUser(String username, String password, boolean admin, String organisationName) {
        if(users.contains(username)) {
            // Throw Exception here
            System.out.println("This username is already taken! Please try another.");
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
     * Hashes a plain-text password
     *
     * @return hashed password
     */
    public String getHashedPassword() {
        return password;
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
     * Gets the User object based on a matching username
     *
     * @return User object from the list of existing user accounts
     */
    public User getUser(String username) {
        User userObject = new User();
        for(User user: userList) {
            if(user.getUsername() == username) {
                userObject = user;
            }
        }
        return userObject;
    }
}
