package ar.edu.itba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserServices;
import ar.edu.itba.models.User;

@Service
public class UserServicesImpl implements UserServices{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User create(String name, String password) {
		return userDao.create(name, password);
	}
	
    @Override
    public User getByUsername(final String username) {
        return userDao.getByUsername(username);
    }

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
