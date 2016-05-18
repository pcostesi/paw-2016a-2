package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.models.PersistableProject;
import ar.edu.itba.models.Project;

@Primary
@Repository
public class ProjectHibernateDao implements ProjectDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public boolean projectExists(Project project) {
		return em.contains(project);
	}

	@Override
	public boolean projectNameExists(String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Project project where project.title = :title", Integer.class);
        query.setParameter("title", title);
        return query.getSingleResult() > 0;
	}

	@Override
	public Project createProject(String title, String description, String code) {
		final PersistableProject persistableProject = PersistableProject.builder()
				.name(title)
				.description(description)
				.code(code)
				.build();
		em.persist(persistableProject);
		em.flush();
		return persistableProject;
	}

	@Override
	public boolean projectCodeExists(String code) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Project project where project.code = :code", Integer.class);
        query.setParameter("code", code);
        return query.getSingleResult() > 0;
	}

	@Override
	public void deleteProject(Project project) {
		PersistableProject persistableProject = (PersistableProject) project;
		em.remove(persistableProject);
	}

	@Override
	public Project updateName(Project project, String name) {
		PersistableProject persistableProject = (PersistableProject) project;
		persistableProject.setName(name);
		return em.merge(persistableProject);
	}

	@Override
	public Project updateDescription(Project project, String description) {
		PersistableProject persistableProject = (PersistableProject) project;
		persistableProject.setDescription(description);
		return em.merge(persistableProject);
	}

	@Override
	public Project updateCode(Project project, String code) {
		PersistableProject persistableProject = (PersistableProject) project;
		persistableProject.setCode(code);
		return em.merge(persistableProject);
	}

	@Override
	public Project getProjectById(int projectId) {
		return em.find(Project.class, projectId);
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
