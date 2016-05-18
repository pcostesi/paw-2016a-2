package ar.edu.itba.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.PersistableBacklogItem;
import ar.edu.itba.models.Project;

public class BacklogHibernateDao implements BacklogDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public BacklogItem createBacklogItem(String title, Optional<String> description, Project project) {
		final PersistableBacklogItem persistableBacklogItem = PersistableBacklogItem.builder()
				.title(title)
				.description(description)
				.projectId(project.projectId())
				.build();
		em.persist(persistableBacklogItem);
		em.flush();
		return persistableBacklogItem;
	}

	@Override
	public boolean backlogItemExists(Project project, String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from BacklogItem backlog where backlog.title = :title and backlog.projectId = :projectId", Integer.class);
		query.setParameter("title", title);
		query.setParameter("projectId", project.projectId());
        return query.getSingleResult() > 0;
	}

	@Override
	public boolean backlogItemExists(BacklogItem backlogItem) {
		return em.contains(backlogItem);
	}

	@Override
	public void deleteItem(BacklogItem backlogItem) {
		PersistableBacklogItem persistableItem = (PersistableBacklogItem) backlogItem;
		em.remove(persistableItem);
	}

	@Override
	public BacklogItem updateTitle(BacklogItem backlogItem, String title) {
		PersistableBacklogItem persistableItem = (PersistableBacklogItem) backlogItem;
		persistableItem.setTitle(title);
		return em.merge(persistableItem);
	}

	@Override
	public BacklogItem updateDescription(BacklogItem backlogItem, Optional<String> description) {
		PersistableBacklogItem persistableItem = (PersistableBacklogItem) backlogItem;
		persistableItem.setDescription(description);
		return em.merge(persistableItem);
	}

	@Override
	public List<BacklogItem> getBacklogForProject(Project project) {
        return project.getBacklogItems();
	}

	@Override
	public Project getParent(BacklogItem backlogItem) {
		return em.find(Project.class, backlogItem.projectId());
	}

	@Override
	public BacklogItem getBacklogItemById(int backlogItemId) {
		return em.find(BacklogItem.class, backlogItemId);
	}

}
