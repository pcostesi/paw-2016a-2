package ar.edu.itba.interfaces;

import java.util.Date;
import java.util.List;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectStatus;

public interface ProjectDao {
	
	public List<Project> getProjetcs();
	
	public Project createProject(String name, String description, Date startDate, ProjectStatus status);

}
