package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.User;

@Repository
public class UserHibernateDao implements UserDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public User getByUsername(String username) {
        return em.find(User.class, username);
	}

	@Override
	public boolean userNameExists(String username) {
		final TypedQuery<User> query = em.createQuery("from User user where user.username = :username", User.class);
        query.setParameter("username", username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	public boolean userMailExists(String mail) {
		final TypedQuery<User> query = em.createQuery("from User user where user.mail = :mail", User.class);
        query.setParameter("mail", mail);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	public User createUser(String name, String password, String mail) {
		final User user = new User(name, password, mail);
		em.persist(user);
		return user;
	}

	@Override
	public List<String> getAllUsernames() {
		final TypedQuery<String> query = em.createQuery("select user.username from User user", String.class);
        return query.getResultList();
	}

}
