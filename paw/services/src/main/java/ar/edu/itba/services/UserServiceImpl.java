package ar.edu.itba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.models.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User create(String name, String password, String mail) {
		if (name == null) {
			throw new IllegalArgumentException("User name can't be null");
		}
		
		if (name.length() < 3) {
			throw new IllegalArgumentException("User name needs at least 3 characters");
		}
		
		if (password == null) {
			throw new IllegalArgumentException("User password can't be null");
		}
		
		if (password.length() < 6) {
			throw new IllegalArgumentException("User password needs at least 6 characters");
		}
		
		if (mail == null) {
			throw new IllegalArgumentException("User mail can't be null");
		}
		
		//TODO falta validar mail
		if (mail.length() < 5) {
			throw new IllegalArgumentException("User mail needs at least 5 characters");
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
    public User getByUsername(final String username) {
    	if (username == null) {
			throw new IllegalArgumentException("User name can't be null");
		}
    	
    	if (!userDao.userNameExists(username)) {
    		throw new IllegalStateException("User doesn't exist");
    	}
    	
    	return userDao.getByUsername(username);
    }

}
