package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.Project;

public interface ProjectDao {
	
	public Project createProject(String name, String description);
	
	public boolean deleteProject(int projectId);
	
	public List<Project> getProjectDetailList();
	
	public Project getProjectWithDetails(String projectName);
	
	public Project getProjectWithDetails(int projectId);

}
