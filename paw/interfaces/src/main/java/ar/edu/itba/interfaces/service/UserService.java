package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

import java.util.List;

public interface UserService {

    /**
     * Create and persist a user.
     *
     * @param name     Username of the new user.
     * @param password Password of the new user.
     * @param mail     Mail of the new user.
     * @return The new user instance.
     */
    User create(final String name, final String password, final String mail);

    /**
     * Get user instance by its user name.
     *
     * @param username User username.
     * @return Instance of the user.
     */
    User getByUsername(final String username);

    /**
     * Get user instance by a stored api key.
     *
     * @param username User username.
     * @return Instance of the user.
     */
    User getByApiKey(final String username);

    /**
     * Get a list of all the users
     *
     * @return List of user instances.
     */
    List<String> getUsernames();

    /**
     * Checks if the username has been used.
     *
     * @param user Username to check.
     * @return True if it exists, false otherwise.
     */
    boolean usernameExists(final String user);

    /**
     * Check if the email has been used.
     *
     * @param mail Mail to check.
     * @return True if mail has been used.
     */
    boolean emailExists(final String mail);

    /**
     * Get a list of usernames exlucding the one from the user passed as parameter.
     *
     * @param user User to exclude.
     * @return List of usernames.
     */
    List<String> getUsernamesExcept(final User user);

    /**
     * Update user password.
     *
     * @param user        User to update.
     * @param newPassword The new password.
     */
    User editPassword(final User user, final String newPassword);

    /**
     * Get list of project members usernames.
     *
     * @param project Project to get members from.
     * @return List of usernames.
     */
    List<String> getUsernamesForProject(final Project project);

    /**
     * Get a list of usernames that aren't members of the project.
     *
     * @param project Project to exclude users from.
     * @return List of usernames.
     */
    List<String> getAvailableUsers(final Project project);

}
