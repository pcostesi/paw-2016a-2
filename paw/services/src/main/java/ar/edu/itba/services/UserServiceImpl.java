package ar.edu.itba.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProjectDao projectDao;

	private MailValidator mailValidator = new MailValidator();

	@Override
	@Transactional
	public User create(String name, String password, String mail) {
		if (name == null) {
			throw new IllegalArgumentException("User name can't be null");
		}

		if (name.length() == 0) {
			throw new IllegalArgumentException("User name can't be empty");
		}

		if (name.length() > 100) {
			throw new IllegalArgumentException("User name can't be longer than 100 characters");
		}

		if (password == null) {
			throw new IllegalArgumentException("User password can't be null");
		}

		if (password.length() == 0) {
			throw new IllegalArgumentException("User password can't be empty");
		}

		if (password.length() > 100) {
			throw new IllegalArgumentException("User password can't have more than 100 characters");
		}

		if (mail == null) {
			throw new IllegalArgumentException("User mail can't be null");
		}

		if (mail.length() == 0) {
			throw new IllegalArgumentException("User mail can't be empty");
		}

		if (mail.length() > 100) {
			throw new IllegalArgumentException("User mail can't be longer than 100 characters");
		}

		if (!mailValidator.validate(mail)) {
			throw new IllegalArgumentException("User mail isn't valid");
		}

		if (userDao.userNameExists(name)) {
			throw new IllegalStateException("This username has been used already");
		}

		if (userDao.userMailExists(mail)) {
			throw new IllegalStateException("This mail has been used already");
		}

		return userDao.createUser(name, password, mail);
	}

	@Override
	@Transactional
	public User getByUsername(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("User name can't be null");
		}

		User user = userDao.getByUsername(username);

		if (user == null) {
			throw new IllegalStateException("User doesn't exist");
		} else {
			return user;
		}
	}

	@Override
	@Transactional
	public List<String> getUsernames() {
		return userDao.getAllUsernames();
	}

	@Override
	@Transactional
	public boolean usernameExists(String username){
		if(username == null){
			throw new IllegalArgumentException("Username can't be null");
		}
		return userDao.userNameExists(username);
	}

	@Override
	@Transactional
	public boolean emailExists(String email){
		if(email == null){
			throw new IllegalArgumentException("Email can't be null");
		}
		return userDao.userMailExists(email);
	}

	private class MailValidator {

		private Pattern pattern;
		private Matcher matcher;

		private static final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		public MailValidator() {
			pattern = Pattern.compile(EMAIL_PATTERN);
		}

		public boolean validate(final String hex) {
			matcher = pattern.matcher(hex);
			return matcher.matches();
		}

	}

	@Override
	public void editPassword(User user, String newPassword) {
		if(user == null){
			throw new IllegalArgumentException("Username cannot be null");
		}

		if(newPassword == null){
			throw new IllegalArgumentException("Password cannot be null");
		}

		userDao.setPassword(user, newPassword);
	}

	@Override
	public List<String> getUsernamesExcept(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User to be excluded can't be null");
		}

		return userDao.getAllUsernamesExcept(user);

	}

	@Override
	public List<String> getMembersUsernames(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		return userDao.getAllUsernamesOfProject(project);
	}

	@Override
	public List<String> getAvailableUsers(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		return userDao.getAvailableUsers(project);
	}

}