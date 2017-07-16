package ar.edu.itba.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.dao.ExperienceDao;
import ar.edu.itba.models.Experience;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.User;

@Primary
@Repository
public class ExperienceHibernateDao implements ExperienceDao{

	@PersistenceContext
    private EntityManager em;

	@Override
	public boolean setExperience(Project project, User user, int experience) {
		try{
			final Query query = em.createQuery("update Experience set experience = :experience where project = :project and user = :user");
			query.setParameter("experience", experience);
			query.setParameter("project", project);
			query.setParameter("user", user);
			return query.executeUpdate() == 1;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update experience entry");
		}
	}

	@Override
	public int getExperience(Project project, User user) {
		final TypedQuery<Integer> query = em.createQuery("select experience from Experience where project = :project and user = :user", Integer.class);
		query.setParameter("project", project);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	@Override
	public boolean hasExperienceInProject(Project project, User user) {
		try {
			final TypedQuery<Long> query = em.createQuery("select count(*) from Experience iteration where project = :project and user = :user", Long.class);
			query.setParameter("project", project);
			query.setParameter("user", user);
	        return query.getSingleResult() > 0;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to check experience entry exists");
		}
	}

	@Override
	public boolean createUserExperience(Project project, User user) {
		try {
			final Experience newExperience = Experience.builder()
					.project(project)
					.user(user)
					.experience(0)
					.build();
			em.persist(newExperience);
			em.flush();
			return true;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to create experience");
		}
	}

	@Override
	public boolean deleteUserExperience(Project project, User user) {
		try {
			final Query query = em.createQuery("delete from Experieince where project = :project and user = :user");
			query.setParameter("project", project);
			query.setParameter("user", user);
			return query.executeUpdate() == 1;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to delete iteration");
		}
	}

	@Override
	public int getTotalExperience(User user) {
		try {
			final TypedQuery<Integer> query = em.createQuery("select sum(experience) from Experience where user = :user", Integer.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to check iteration exists");
		}
	}

}
