package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.models.Project;

public class ProjectHibernateDao implements ProjectDao{

	@Override
	public boolean projectExists(int projectId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean projectNameExists(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Project createProject(String name, String description, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean projectCodeExists(String code) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteProject(int projectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateName(int projectId, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDescription(int projectId, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCode(int projectId, String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Project getProjectById(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getProjectByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
