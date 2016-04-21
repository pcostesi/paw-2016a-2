package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	IterationDao iterationDao;

	@Override
	public Project createProject(String name, String description, String code) {
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Project name needs at least 1 character");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Description needs at least 1 character");
		}
		
		if (code == null) {
			throw new IllegalArgumentException("Project code can't be null");
		}
		
		if (code.length() == 0) {
			throw new IllegalArgumentException("Project code needs at least 1 character");
		}
		
		if (projectDao.projectNameExists()) {
			throw new IllegalStateException("Project name has been used already");
		}
		
		if (projectDao.projectCodeExists()) {
			throw new IllegalStateException("Project code has been used already");
		}

		return projectDao.createProject(name, description, code);
	}

	@Override
	public void deleteProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!projectDao.deleteProject(project.getProjectId())) {
			throw new IllegalStateException("Project delete failed");
		}
	}

	@Override
	public List<Iteration> getIterationsForProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		return iterationDao.getIterationsForProject(project.getProjectId());
	}

	@Override
	public Project setName(Project project, String name) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Project name needs at least 1 character");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!projectDao.updateName(project.getProjectId(), name)) {
			throw new IllegalStateException("Project name update failed");
		} else {
			project.setName(name);
			return project;
		}
	}

	@Override
	public Project setDescription(Project project, String description) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Project description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Project description needs at least 1 character");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!projectDao.updateDescription(project.getProjectId(), description)) {
			throw new IllegalStateException("Project description update failed");
		} else {
			project.setDescription(description);
			return project;
		}
	}

	@Override
	public Project setCode(Project project, String code) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (code == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (code.length() == 0) {
			throw new IllegalArgumentException("Project name needs at least 1 character");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!projectDao.updateCode(project.getProjectId(), code)) {
			throw new IllegalStateException("Project code name update failed");
		} else {
			project.setCode(code);
			return project;
		}
	}

	@Override
	public Project getProjectById(int projectId) {
		if (projectId < 0) {
			throw new IllegalArgumentException("Invalid project id");
		}
		
		Project project = projectDao.getProjectById(projectId);
		
		if (project == null) {
			throw new IllegalStateException("Project doesn't exist");
		} else {
			return project;
		}
	}	

}
