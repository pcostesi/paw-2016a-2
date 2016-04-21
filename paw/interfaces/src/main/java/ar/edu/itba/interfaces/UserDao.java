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
    public User create(final String username, final String password, final String mail);
    
    /**
     * Fetch a user by username
     * @param username The user's name.
     * @return The matched user, or null if none matches.
     */
    public User getByUsername(final String username);

	public boolean userNameExists(final String name);

	public boolean userMailExists(final String mail);

	public User createUser(final String name, final String password, final String mail);
    
}