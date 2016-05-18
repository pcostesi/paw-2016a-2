package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Project;

public interface ProjectDao {

	public boolean projectExists(final Project project);

	public boolean projectNameExists(final String name);

	public Project createProject(final String name, final String description, final String code);

	public boolean projectCodeExists(String code);

	public void deleteProject(final Project project);

	public Project updateName(final Project project, final String name);

	public Project updateDescription(final Project project, final String description);

	public Project updateCode(final Project project, final String code);

	public Project getProjectById(final int projectId);

	public List<? extends Project> getProjects();

	public Project getProjectByCode(final String code);

}
