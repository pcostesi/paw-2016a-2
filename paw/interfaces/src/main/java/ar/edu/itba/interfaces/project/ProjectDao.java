package ar.edu.itba.interfaces.project;

import java.util.List;

import ar.edu.itba.models.project.Project;
import ar.edu.itba.models.project.ProjectDetail;

public interface ProjectDao {
	
	public ProjectDetail createProject(String name, String description);
	
	public boolean deleteProject(int projectId);
	
	public List<ProjectDetail> getProjectDetailList();
	
	public Project getProjectWithDetails(String projectName);
	
	public Project getProjectWithDetails(int projectId);

}
