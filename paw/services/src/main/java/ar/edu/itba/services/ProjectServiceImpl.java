package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.ProjectDetail;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	ProjectDao projectDao;
	
	@Override
	public ProjectDetail createProject(String name, String description) {
		return projectDao.createProject(name, description);
	}

	@Override
	public boolean deleteProject(int projectId) {
		return projectDao.deleteProject(projectId);
	}

	@Override
	public List<ProjectDetail> getProjectDetailList() {
		return projectDao.getProjectDetailList();
	}

	@Override
	public Project getProjectWithDetails(String projectName) {
		return projectDao.getProjectWithDetails(projectName);
	}

	@Override
	public Project getProjectWithDetails(int projectId) {
		return projectDao.getProjectWithDetails(projectId);
	}

}
