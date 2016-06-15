package ar.edu.itba.interfaces.dao;

import java.util.List;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

public interface ProjectDao {

	public boolean projectExists(final Project project);

	public boolean projectNameExists(final String name);

	public Project createProject(final User admin, final String name, final String description, final String code);

	public boolean projectCodeExists(String code);

	public void deleteProject(final Project project);

	public void updateName(final Project project, final String name);

	public void updateDescription(final Project project, final String description);

	public void updateCode(final Project project, final String code);

	public Project getProjectById(final int projectId);

	public List<Project> getProjectsForUser(final User user);

	public Project getProjectByCode(final String code);

	public void addProjectMember(final Project project, final User user);

	public List<User> getProjectMembers(final Project project);

	void deleteProjectUser(final Project project);

	public void deleteProjectMember(final Project project, final User userToDelete);

	public boolean userBelongsToProject(final Project project, final User user);

}
