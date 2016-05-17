package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.User;

@Repository
public class UserHibernateDao implements UserDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public User getByUsername(String username) {
		return null;
	}

	@Override
	public boolean userNameExists(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userMailExists(String mail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User createUser(String name, String password, String mail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllUsernames() {
		// TODO Auto-generated method stub
		return null;
	}

}
