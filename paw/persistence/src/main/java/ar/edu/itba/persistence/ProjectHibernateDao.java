package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.models.Project;

public class ProjectHibernateDao implements ProjectDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public boolean projectExists(int projectId) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Project project where project.projectId = :projectId", Integer.class);
        query.setParameter("projectId", projectId);
        return query.getSingleResult() > 0;
	}

	@Override
	public boolean projectNameExists(String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Project project where project.title = :title", Integer.class);
        query.setParameter("title", title);
        return query.getSingleResult() > 0;
	}

	@Override
	public Project createProject(String title, String description, String code) {
		final Project project = Project.builder()
				.name(title)
				.description(description)
				.code(code)
				.build();
		em.persist(project);
		em.flush();
		return project;
	}

	@Override
	public boolean projectCodeExists(String code) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Project project where project.code = :code", Integer.class);
        query.setParameter("code", code);
        return query.getSingleResult() > 0;
	}

	@Override
	public void deleteProject(int projectId) {
		final TypedQuery<Integer> query = em.createQuery("delete from Project where projectId = :projectId", Integer.class);
		query.setParameter("projectId", projectId);
		query.executeUpdate();
	}

	@Override
	public void updateName(int projectId, String title) {
		final TypedQuery<Integer> query = em.createQuery("update Project set title = :title where projectId = :projectId", Integer.class);
		query.setParameter("projectId", projectId);
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	public void updateDescription(int projectId, String description) {
		final TypedQuery<Integer> query = em.createQuery("update Project set description = :description where projectId = :projectId", Integer.class);
		query.setParameter("projectId", projectId);
		query.setParameter("description", description);
		query.executeUpdate();
	}

	@Override
	public void updateCode(int projectId, String code) {
		final TypedQuery<Integer> query = em.createQuery("update Project set code = :code where projectId = :projectId", Integer.class);
		query.setParameter("projectId", projectId);
		query.setParameter("code", code);
		query.executeUpdate();
	}

	@Override
	public Project getProjectById(int projectId) {
		final TypedQuery<Project> query = em.createQuery("from Project project where project.projectId = :projectId", Project.class);
        query.setParameter("projectId", projectId);
        return query.getSingleResult();
	}

	@Override
	public List<Project> getProjects() {
		final TypedQuery<Project> query = em.createQuery("from Project", Project.class);
        return query.getResultList();
	}

	@Override
	public Project getProjectByCode(String code) {
		final TypedQuery<Project> query = em.createQuery("from Project project where project.code = :code", Project.class);
        query.setParameter("code", code);
        return query.getSingleResult();
	}

}
