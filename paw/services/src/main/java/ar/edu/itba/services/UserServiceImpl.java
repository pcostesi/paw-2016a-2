package ar.edu.itba.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
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
	public List<String> getUsernames() {
		return userDao.getAllUsernames();
	}
	
	public boolean usernameExists(String username){
		if(username == null){
			throw new IllegalArgumentException("Username is null");
		}
		return userDao.userNameExists(username);
	}
	
	public boolean emailExists(String email){
		if(email == null){
			throw new IllegalArgumentException("Email is null");
		}
		return userDao.userMailExists(email);
	}

}