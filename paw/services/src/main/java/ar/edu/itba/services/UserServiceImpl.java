package ar.edu.itba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.user.UserDao;
import ar.edu.itba.interfaces.user.UserService;
import ar.edu.itba.models.user.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User create(String name, String password, String mail) {
		return userDao.create(name, password, mail);
	}
	
    @Override
    public User getByUsername(final String username) {
        return userDao.getByUsername(username);
    }

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
