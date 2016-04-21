package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public interface ProjectService {

	public Project createProject(final String name, final String description, final String code);
	
	public void deleteProject(final Project project);
	
	public Project getProjectById(final int projectId);
	
	public List<Iteration> getIterationsForProject(final Project project);
	
	public Project setName(final Project project, final String name);

	public Project setDescription(final Project project, final String description);

	public Project setCode(final Project project, final String code);
}
