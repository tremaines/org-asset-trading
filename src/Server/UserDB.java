package Server;

import Client.User;

public interface UserDB {

    /**
     * Check if a username already exists
     * @param username The username to be checked
     * @return True if it exists, false otherwise
     */
    boolean checkUsername(String username);

    /**
     * Get a user
     * @param username The username of the user
     * @return An instance of the User object with details of the user
     */
    User getUser(String username);

    /**
     * Add a new user
     * @param user A User object
     */
    void addUser(User user);

    /**
     * Update an existing user
     * @param user A User object containing updated details of the user
     */
    void update(User user);
}
