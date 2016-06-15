package ar.edu.itba.interfaces.service;

import java.util.List;
import java.util.Set;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

public interface ProjectService {

	/**
	 * Create a project instance.
	 * @param admin Project admin.
	 * @param members List of project members (admin is added to the list).
	 * @param name Name of the project.
	 * @param description Brief project description.
	 * @param code Identifying code.
	 * @return
	 */
	public Project createProject(final User admin, final Set<User> members, final String name, final String description, final String code);
	
	/**
	 * Delete project instance from database.
	 * @param user User to perform the deletion (checks for permission).
	 * @param project Project to delete.
	 */
	public void deleteProject(final User user, final Project project);
	
	/**
	 * Get a project instance by its id
	 * @param projectId Id of the project
	 * @return the project instance
	 */
	public Project getProjectById(final int projectId);
	
	/**
	 * Update the project name to a new one
	 * @param user User to perform the update (checks for permission).
	 * @param project Project to set a new name to.
	 * @param name New project name.
	 * @return An instance with the new name.
	 */
	public Project setName(final User user, final Project project, final String name);

	/**
	 * Update the project description to a new one
	 * @param user User to perform the update (checks for permission).
	 * @param project Project to set a new description to.
	 * @param description New project description (can be null).
	 * @return An instance with the new description.
	 */
	public Project setDescription(final User user, final Project project, final String description);

	/**
	 * Update the project code to a new one
	 * @param user User to perform the update (checks for permission).
	 * @param project Project to set a new code to.
	 * @param description New project code.
	 * @return An instance with the new code.
	 */
	public Project setCode(final User user, final Project project, final String code);
	
	/**
	 * Gets a list of all the project the user is a member of.
	 * @param user User to get project for.
	 * @return List of the project instances the user is a member of.
	 */
	public List<Project> getProjectsForUser(final User user);

	/**
	 * Get a project by its code.
	 * @param code Project code.
	 * @return Instance of the project.
	 */
	public Project getProjectByCode(final String code);

	/**
	 * Check if the project code was used by another project.
	 * @param code Code to check.
	 * @return True if it was used by another project, false otherwise.
	 */
	public boolean projectCodeExists(final String code);

	/**
	 * Checks if the project name has been used by another project
	 * @param name Name to check
	 * @return True if its been used, false otherwise.
	 */
	public boolean projectNameExists(final String name);
	
	/**
	 * Get a list of the project members.
	 * @param project Project to get the list of members from.
	 * @return A list of the user instances.
	 */
	public List<User> getProjectMembers(final Project project);
	
	/**
	 * Add a user as member to the project.
	 * @param user User to perform the addition (must have privilege to add people).
	 * @param project Project instance to add the user to.
	 * @param userToAdd User to be added.
	 */
	public void addUserToProject(final User user, final Project project, final User userToAdd);

	/**
	 * Delete a user membership from the project.
	 * @param user User to perform the deletion (must have privilege to remove people).
	 * @param project Project to remove the user from.
	 * @param userToDelete User that will be removed from the project.
	 */
	public void deleteUserFromProject(final User user, final Project project, final User userToDelete);
	
	/**
	 * Checks if the user belongs to the project.
	 * @param project Project to check the users belongs to.
	 * @param user User to check.
	 * @return True if it belongs, false otherwise.
	 */
	public boolean userBelongsToProject(final Project project, final User user);
	
}
