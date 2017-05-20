package ar.edu.itba.interfaces.service;

import java.util.List;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

public interface UserService {
	
	/**
	 * Create and persist a user.
	 * @param name Username of the new user.
	 * @param password Password of the new user.
	 * @param mail Mail of the new user.
	 * @return The new user instance.
	 */
	public User create(final String name, final String password, final String mail);
	
	/**
	 * Get user instance by its user name.
	 * @param username User username.
	 * @return Instance of the user.
	 */
    public User getByUsername(final String username);
    
	/**
	 * Get user instance by a stored api key.
	 * @param username User username.
	 * @return Instance of the user.
	 */
    public User getByApiKey(final String username);
    
    /**
     * Get a list of all the users
     * @return List of user instances.
     */
    public List<String> getUsernames();

    /**
     * Checks if the username has been used.
     * @param user Username to check.
     * @return True if it exists, false otherwise.
     */
	public boolean usernameExists(final String user);

	/**
	 * Check if the email has been used.
	 * @param mail Mail to check. 
	 * @return True if mail has been used.
	 */
	public boolean emailExists(final String mail);

	/**
	 * Get a list of usernames exlucding the one from the user passed as parameter.
	 * @param user User to exclude.
	 * @return List of usernames.
	 */
	public List<String> getUsernamesExcept(final User user);

	/**
	 * Update user password.
	 * @param user User to update.
	 * @param newPassword The new password.
	 */
	public User editPassword(final User user, final String newPassword);

	/**
	 * Get list of project members usernames.
	 * @param project Project to get members from.
	 * @return List of usernames.
	 */
	public List<String> getUsernamesForProject(final Project project);

	/**
	 * Get a list of usernames that aren't members of the project.
	 * @param project Project to exclude users from.
	 * @return List of usernames.
	 */
	public List<String> getAvailableUsers(final Project project);
  
}
