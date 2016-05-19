package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.User;

@Primary
@Repository
public class UserHibernateDao implements UserDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	@Transactional
	public User getByUsername(String username) {
        return em.find(User.class, username);
	}

	@Override
	@Transactional
	public boolean userNameExists(String username) {
		final TypedQuery<User> query = em.createQuery("from User user where user.username = :username", User.class);
        query.setParameter("username", username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	@Transactional
	public boolean userMailExists(String mail) {
		final TypedQuery<User> query = em.createQuery("from User user where user.mail = :mail", User.class);
        query.setParameter("mail", mail);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	@Transactional
	public User createUser(String name, String password, String mail) {
		final User persistableUser = User.builder()
				.username(name)
				.password(password)
				.mail(mail)
				.build();
		em.persist(persistableUser);
		return persistableUser;
	}

	@Override
	@Transactional
	public List<String> getAllUsernames() {
		final TypedQuery<String> query = em.createQuery("select user.username from User user", String.class);
        return query.getResultList();
	}

}
