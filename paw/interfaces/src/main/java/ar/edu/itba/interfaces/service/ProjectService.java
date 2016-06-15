package ar.edu.itba.interfaces.service;

import java.util.List;
import java.util.Set;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

public interface ProjectService {

	public Project createProject(final User admin, final Set<User> members, final String name, final String description, final String code);
	
	public void deleteProject(final User user, final Project project);
	
	public Project getProjectById(final int projectId);
	
	public Project setName(final User user, final Project project, final String name);

	public Project setDescription(final User user, final Project project, final String description);

	public Project setCode(final User user, final Project project, final String code);
	
	public List<Project> getProjectsForUser(final User user);

	public Project getProjectByCode(final String code);

	public boolean projectCodeExists(final String code);

	public boolean projectNameExists(final String name);
	
	public List<User> getProjectMembers(final Project project);
	
	public void addUserToProject(final User user, final Project project, final User userToAdd);

	public void deleteUserFromProject(final User user, final Project project, final User userToDelete);
	
	public boolean userBelongsToProject(final Project project, final User user);
	
}
