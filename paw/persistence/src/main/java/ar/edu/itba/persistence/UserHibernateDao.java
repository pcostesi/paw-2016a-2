package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.UserDao;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Primary
@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User getByUsername(final String username) {
        try {
            if (userNameExists(username)) {
                return em.find(User.class, username);
            } else {
                return null;
            }
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get user by username");
        }
    }

    @Override
    @Transactional
    public boolean userNameExists(final String username) {
        try {
            final TypedQuery<User> query = em.createQuery("from User user where lower(user.username) = :username", User.class);
            query.setParameter("username", username.toLowerCase());
            final List<User> list = query.getResultList();
            return !list.isEmpty();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check username exists");
        }
    }

    @Override
    @Transactional
    public boolean userMailExists(final String mail) {
        try {
            final TypedQuery<User> query = em.createQuery("from User user where user.mail = :mail", User.class);
            query.setParameter("mail", mail);
            final List<User> list = query.getResultList();
            return !list.isEmpty();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check mail is used");
        }
    }

    @Override
    @Transactional
    public User createUser(final String name, final String password, final String mail) {
        try {
            final User persistableUser = User.builder()
                    .username(name)
                    .password(password)
                    .mail(mail)
                    .build();
            em.persist(persistableUser);
            em.flush();
            return persistableUser;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to create user");
        }
    }

    @Override
    @Transactional
    public List<String> getAllUsernames() {
        try {
            final TypedQuery<String> query = em.createQuery("select user.username from User user", String.class);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get all usernames");
        }
    }

    @Override
    @Transactional
    public List<String> getAllUsernamesExcept(final User user) {
        try {
            final TypedQuery<String> query = em.createQuery("select user.username from User user where user != :user", String.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get all usernames except one");
        }
    }

    @Override
    @Transactional
    public void setPassword(final User user, final String newPassword) {
        try {
            final Query query = em.createQuery("update User set password = :password where username = :username");
            query.setParameter("username", user.username());
            query.setParameter("password", newPassword);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update user password");
        }
    }

    @Override
    @Transactional
    public List<String> getAllUsernamesOfProject(final Project project) {
        try {
            final TypedQuery<String> query = em.createQuery("select pj.user.username from ProjectUser pj where project = :project ", String.class);
            query.setParameter("project", project);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get all usernames of project");
        }
    }

    @Override
    @Transactional
    public List<String> getAvailableUsers(final Project project) {
        final TypedQuery<String> query = em.createQuery("select user.username from User user where user not in (select pu.user from ProjectUser pu where pu.project = :project)", String.class);
        query.setParameter("project", project);
        return query.getResultList();
    }

}
