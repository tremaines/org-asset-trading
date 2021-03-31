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
    private List<String> users;
    // Password to login to the marketplace
    private String password;
    // Hashed value of the password
    private String hashedPassword;
    // Type of account (admin or non-admin)
    private String accountType;
    // Organisation the account belongs to
    private String organisationName;

    /**
     * User constructor that creates an authorised account from an organisation
     *
     * @param username which will be used to login
     * @param password used to login
     * @param accountType either admin or non-admin user
     * @param organisationName name of the organisation the account is a part of
     */
    public User(String username, String password, String accountType, String organisationName) {
        users = new ArrayList<>();
        if(users.contains(username)) {
            System.out.println("This username is already taken! Please try another.");
        }
        else {
            users.add(username);
            this.username = username;
            this.password = password;
            this.hashedPassword = (password.hashCode() * 2.334) + "";
            this.accountType = accountType;
            this.organisationName = organisationName;
        }
    }

    /**
     * Hashes a plain-text password
     *
     * @return hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
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
     * Displays all usernames registered on the platform
     */
    private void getAllUsers() {
        for(String u: users) {
            System.out.println(u);
        }
    }
}
