package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Project;

public interface ProjectDao {
	
	public List<Project> getProjetcs();
	
	public Project createProject(String title, String description);

}
