package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	IterationDao iterationDao;

	@Override
	public Project createProject(final String name, final String description, final String code) {
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Project name can't be empty");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Description can't be empty");
		}
		
		if (code == null) {
			throw new IllegalArgumentException("Project code can't be null");
		}
		
		if (code.length() == 0) {
			throw new IllegalArgumentException("Project code can't be empty");
		}
		
		if (code.length() > 10) {
			throw new IllegalArgumentException("Project code can't have more than 10 characters");
		}
		
		if (projectDao.projectNameExists(name)) {
			throw new IllegalStateException("This project name has been used already");
		}
		
		if (projectDao.projectCodeExists(code)) {
			throw new IllegalStateException("This project code has been used already");
		}

		return projectDao.createProject(name, description, code);
	}

	@Override
	public void deleteProject(final Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		projectDao.deleteProject(project.getProjectId());
	}

	@Override
	public Project setName(final Project project, final String name) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Project name can't be empty");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		projectDao.updateName(project.getProjectId(), name);
		project.setName(name);
		
		return project;
	}

	@Override
	public Project setDescription(final Project project, final String description) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Project description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Project description can't be empty");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		projectDao.updateDescription(project.getProjectId(), description);
		project.setDescription(description);
		
		return project;
	}

	@Override
	public Project setCode(final Project project, final String code) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (code == null) {
			throw new IllegalArgumentException("Project code can't be null");
		}
		
		if (code.length() == 0) {
			throw new IllegalArgumentException("Project code can't be empty");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		projectDao.updateCode(project.getProjectId(), code);
		project.setCode(code);
		
		return project;
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

	@Override
	public List<Project> getProjects() {
		return projectDao.getProjects();	
	}

	@Override
	public Project getProjectByCode(String code) {
		if (code == null) {
			throw new IllegalArgumentException("Project code can't be null");
		}
		
		if (code.length() == 0) {
			throw new IllegalArgumentException("Project code can't be empty");
		}
		
		Project project = projectDao.getProjectByCode(code);
		
		if (project == null) {
			throw new IllegalStateException("Project doesn't exist");
		} else {
			return project;
		}
	}	

}
