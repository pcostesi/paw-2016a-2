package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.HibernateUser;
import ar.edu.itba.models.User;

@Repository
public class UserHibernateDao implements UserDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public User getByUsername(String username) {
        return em.find(HibernateUser.class, username);
	}

	@Override
	public boolean userNameExists(String username) {
		final TypedQuery<HibernateUser> query = em.createQuery("from HibernateUser user where user.username = :username", HibernateUser.class);
        query.setParameter("username", username);
        final List<HibernateUser> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	public boolean userMailExists(String mail) {
		final TypedQuery<HibernateUser> query = em.createQuery("from HibernateUser user where user.mail = :mail", HibernateUser.class);
        query.setParameter("mail", mail);
        final List<HibernateUser> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	public User createUser(String name, String password, String mail) {
		final HibernateUser user = new HibernateUser(name, password, mail);
		em.persist(user);
		return user;
	}

	@Override
	public List<String> getAllUsernames() {
		final TypedQuery<String> query = em.createQuery("select user.username from HibernateUser user", String.class);
        return query.getResultList();
	}

}
