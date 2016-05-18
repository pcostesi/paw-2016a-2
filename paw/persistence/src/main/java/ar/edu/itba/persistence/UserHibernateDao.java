package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.PersistableUser;
import ar.edu.itba.models.User;

@Primary
@Repository
public class UserHibernateDao implements UserDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public User getByUsername(String username) {
        return em.find(PersistableUser.class, username);
	}

	@Override
	public boolean userNameExists(String username) {
		final TypedQuery<PersistableUser> query = em.createQuery("from PersistableUser user where user.username = :username", PersistableUser.class);
        query.setParameter("username", username);
        final List<PersistableUser> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	public boolean userMailExists(String mail) {
		final TypedQuery<PersistableUser> query = em.createQuery("from PersistableUser user where user.mail = :mail", PersistableUser.class);
        query.setParameter("mail", mail);
        final List<PersistableUser> list = query.getResultList();
        return list.isEmpty() ? false : true;
	}

	@Override
	public User createUser(String name, String password, String mail) {
		final PersistableUser persistableUser = PersistableUser.builder()
				.username(name)
				.password(password)
				.mail(mail)
				.build();
		em.persist(persistableUser);
		return persistableUser;
	}

	@Override
	public List<String> getAllUsernames() {
		final TypedQuery<String> query = em.createQuery("select user.username from PersistableUser user", String.class);
        return query.getResultList();
	}

}
