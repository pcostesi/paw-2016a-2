package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectDetail;

public interface ProjectDao {
	
	public ProjectDetail createProject(String name, String description);
	
	public boolean deleteProject(int projectId);
	
	public List<ProjectDetail> getProjectDetailList();
	
	public Project getProjectWithDetails(String projectName);
	
	public Project getProjectWithDetails(int projectId);

}
