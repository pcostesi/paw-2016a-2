package ar.edu.itba.services;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.ProjectService;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private UserDao userDao;

	private CodeValidator codeValidator = new CodeValidator();

	@Override
	public Project createProject(final User admin, final Set<User> members, final String name, final String description, final String code) {
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

		if (admin == null) {
			throw new IllegalArgumentException("Project admin can't be null");
		}

		if (members == null) {
			throw new IllegalArgumentException("Project members can't be null");
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

		Project project = projectDao.createProject(admin, name, description, code);

		projectDao.addProjectMember(project, admin);
		for (User user: members) {
			projectDao.addProjectMember(project, user);
		}

		return projectDao.getProjectById(project.projectId());
	}

	@Override
	public void deleteProject(final User user, final Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}

		if (user == null) {
			throw new IllegalArgumentException("Project admin can't be null");
		}

		if (!project.admin().equals(user)) {
			throw new IllegalStateException("This user doesnt isn't the project admin");
		}

		projectDao.deleteProjectUser(project);
		projectDao.deleteProject(project);
	}

	@Override
	public Project setName(final User user, final Project project, final String name) {
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

		if (user == null) {
			throw new IllegalArgumentException("Project admin can't be null");
		}

		if (!project.admin().equals(user)) {
			throw new IllegalStateException("This user doesnt isn't the project admin");
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

		projectDao.updateName(project, name);

		return projectDao.getProjectById(project.projectId());
	}

	@Override
	public Project setDescription(final User user, final Project project, final String description) {
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

		if (user == null) {
			throw new IllegalArgumentException("Project admin can't be null");
		}

		if (!project.admin().equals(user)) {
			throw new IllegalStateException("This user doesnt isn't the project admin");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}

		if (project.description().equals(description)) {
			return project;
		}		

		projectDao.updateDescription(project, description);

		return projectDao.getProjectById(project.projectId());
	}

	@Override
	public Project setCode(final User user, final Project project, final String code) {
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

		if (user == null) {
			throw new IllegalArgumentException("Project admin can't be null");
		}

		if (!project.admin().equals(user)) {
			throw new IllegalStateException("This user doesnt isn't the project admin");
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

		projectDao.updateCode(project, code);

		return projectDao.getProjectById(project.projectId());
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
	public List<Project> getProjectsForUser(final User user) {
		if (user == null) {
			throw new IllegalArgumentException("Can't retrieve project for null user");
		}

		return projectDao.getProjectsForUser(user);	
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

	@Override
	public List<User> getProjectMembers(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}

		return projectDao.getProjectMembers(project);
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
	public void addUserToProject(User user, Project project, User userToAdd) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (user == null) {
			throw new IllegalArgumentException("User can't be null");
		}
		
		if (userToAdd == null) {
			throw new IllegalArgumentException("User to add can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!project.admin().equals(user)) {
			throw new IllegalStateException("You dont have permission to add users");
		}
		
		if (projectDao.userBelongsToProject(project, userToAdd)) {
			throw new IllegalStateException("The user to add already belongs to the project");
		}
		
		if (!userDao.userNameExists(userToAdd.username())) {
			throw new IllegalStateException("User to add doesn't exist");
		}
		
		if (!userDao.userNameExists(user.username())) {
			throw new IllegalStateException("User doesn't exist");
		}
		
		projectDao.addProjectMember(project, userToAdd);
	}

	@Override
	public void deleteUserFromProject(User user, Project project, User userToDelete) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (user == null) {
			throw new IllegalArgumentException("User can't be null");
		}
		
		if (userToDelete == null) {
			throw new IllegalArgumentException("User to delete can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!project.admin().equals(user)) {
			throw new IllegalStateException("You dont have permission to delete users");
		}
		
		if (user.equals(userToDelete)) {
			throw new IllegalStateException("Can't delete yourself from project");
		}
		
		if (!userDao.userNameExists(userToDelete.username())) {
			throw new IllegalStateException("User to delete doesn't exist");
		}
		
		if (!userDao.userNameExists(user.username())) {
			throw new IllegalStateException("User doesn't exist");
		}
		
		projectDao.deleteProjectMember(project, userToDelete);
	}

	@Override
	public boolean userBelongsToProject(Project project, User user) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (user == null) {
			throw new IllegalArgumentException("User can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		if (!userDao.userNameExists(user.username())) {
			throw new IllegalStateException("User doesn't exist");
		}
		
		return projectDao.userBelongsToProject(project, user);
	}

}