package ar.edu.itba.interfaces;

import ar.edu.itba.models.User;

public interface UserDao {

    /**
     * Create a new user.
     * 
     * @param username The name of the user.
     * @param password The user's password.
     * @return The created user.
     */
    public User create(String username, String password);
    
    /**
     * Fetch a user by username
     * @param username The user's name.
     * @return The matched user, or null if none matches.
     */
    public User getByUsername(String username);
    
}