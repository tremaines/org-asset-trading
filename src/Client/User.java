package Client;

/**
 * Users are accounts which have been authorised by a particular organisation unit
 * to engage on the electronic trading marketplace.
 */
public class User {

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
    private String unit;

    /**
     * User constructor that creates an authorised account from an organisation
     *
     * @param username Name of the user's account
     * @param password Password to log in to the account
     * @param admin Admin status (true/false)
     * @param unit Name of the unit the user is associated with
     */
    public User (String firstName, String lastName, String email, String username,
                 String password, boolean admin, String unit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        // Hashed password
        this.password = hashPassword(password);
        this.admin = admin;
        this.unit = unit;
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
    public String getUnitName() { return unit; }

//    /**
//     * Checks whether a the username and password details entered match those of the actual user
//     * account
//     *
//     * @param username Name of the user account
//     * @param password Password for the user account
//     * @return True or false
//     */
//    public boolean loginSuccessful(String username, String password) {
//        if(users.contains(username)) {
//            User userToLogin = getUser(username);
//
//            String hashedPassword = hashPassword(password);
//
//            if(hashedPassword.equals(userToLogin.getHashedPassword())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Checks the list of usernames to verify whether the username entered exists within the list
//     *
//     * @param username Name of the user account
//     * @return True or false
//     */
//    public boolean userExists(String username) {
//        if(users.contains(username)) {
//            User userToLogin = getUser(username);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Gets the User object based on a matching username
//     *
//     * @return User object of the user account
//     */
//    public User getUser(String username) {
//        User userObject = new User(org);
//
//        for(int i = 0; i < userList.size(); i++) {
//            if(userList.get(i).getUsername().equals(username)) {
//                userObject = userList.get(i);
//            }
//        }
//        return userObject;
//    }
}
