package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Project;

public interface ProjectDao {

	public boolean projectExists(final int projectId);

	public boolean projectNameExists(final String name);

	public Project createProject(final String name, final String description, final String code);

	public boolean projectCodeExists(String code);

	public void deleteProject(final int projectId);

	public void updateName(final int projectId, final String name);

	public void updateDescription(final int projectId, final String description);

	public void updateCode(final int projectId, final String code);

	public Project getProjectById(final int projectId);

	public List<Project> getProjects();

	public Project getProjectByCode(final String code);

}
