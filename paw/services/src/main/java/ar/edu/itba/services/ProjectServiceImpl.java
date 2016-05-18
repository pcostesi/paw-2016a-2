package ar.edu.itba.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.models.Project;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectDao projectDao;
	
	private CodeValidator codeValidator = new CodeValidator();

	@Override
	public Project createProject(final String name, final String description, final String code) {
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Project name can't be empty");
		}
		
		if (name.length() > 100) {
			throw new IllegalArgumentException("Project name can't be longer than 100 characters");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Description can't be empty");
		}
		
		if (description.length() > 500) {
			throw new IllegalArgumentException("Project name can't be longer than 500 characters");
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
		
		if (!codeValidator.validate(code)) {
			throw new IllegalArgumentException("Project code can only have numbers and lower case characters");
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
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		projectDao.deleteProject(project);
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
		
		if (name.length() > 100) {
			throw new IllegalArgumentException("Project name can't be longer than 100 characters");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (project.name().equals(name)) {
			return project;
		}
		
		if (projectDao.projectNameExists(name)) {
			throw new IllegalStateException("This project name has been used already");
		}		
		
		return projectDao.updateName(project, name);
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
		
		if (description.length() > 500) {
			throw new IllegalArgumentException("Project description can't be longer than 100 characters");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (project.description().equals(description)) {
			return project;
		}		
		
		return projectDao.updateDescription(project, description);
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
		
		if (code.length() > 10) {
			throw new IllegalArgumentException("Project code can't be longer than 10 characters");
		}
		
		if (!codeValidator.validate(code)) {
			throw new IllegalArgumentException("Project code can only have numbers and lower case characters");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (project.code().equals(code)) {
			return project;
		}
		
		if (projectDao.projectCodeExists(code)) {
			throw new IllegalStateException("This project code has been used already");
		}
		
		return projectDao.updateCode(project, code);
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
		
		if (code.length() > 10) {
			throw new IllegalArgumentException("Project code can't be longer than 10 characters");
		}
		
		if (!codeValidator.validate(code)) {
			throw new IllegalArgumentException("Project code can only have numbers and lower case characters");
		}
		
		Project project = projectDao.getProjectByCode(code);
		
		if (project == null) {
			throw new IllegalStateException("Project doesn't exist");
		} else {
			return project;
		}
	}	
	
	private class CodeValidator {

    	private Pattern pattern;
    	private Matcher matcher;

    	private static final String CODE_PATTERN = "[A-Za-z0-9]+";

    	public CodeValidator() {
    		pattern = Pattern.compile(CODE_PATTERN);
    	}

    	public boolean validate(final String hex) {
    		matcher = pattern.matcher(hex);
    		return matcher.matches();
    	}
    	
    }

	@Override
	public boolean projectCodeExists(String code) {
		if (code == null) {
			throw new IllegalArgumentException("Project code can't be null");
		}
		
		return projectDao.projectCodeExists(code);
	}

	@Override
	public boolean projectNameExists(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		return projectDao.projectNameExists(name);
	}

}