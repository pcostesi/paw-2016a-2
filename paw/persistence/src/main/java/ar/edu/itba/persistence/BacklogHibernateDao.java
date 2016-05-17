package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.models.BacklogItem;

public class BacklogHibernateDao implements BacklogDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public BacklogItem createBacklogItem(String title, String description, int projectId) {
		final BacklogItem backlogItem = BacklogItem.builder()
				.title(title)
				.description(description)
				.projectId(projectId)
				.build();
		em.persist(backlogItem);
		em.flush();
		return backlogItem;
	}

	@Override
	public boolean backlogItemExists(String title, int projectId) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from BacklogItem backlog where backlog.title = :title and backlog.projectId = :projectId", Integer.class);
		query.setParameter("title", title);
		query.setParameter("projectId", projectId);
        return query.getSingleResult() > 0;
	}

	@Override
	public boolean backlogItemExists(int itemId) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from BacklogItem backlog where backlog.backlogItemId = :backlogItemId", Integer.class);
		query.setParameter("itemId", itemId);
        return query.getSingleResult() > 0;
	}

	@Override
	public void deleteItem(int backlogItemId) {
		final TypedQuery<Integer> query = em.createQuery("delete from BacklogItem where backlogItemId = :backlogItemId", Integer.class);
		query.setParameter("backlogItemId", backlogItemId);
		query.executeUpdate();
	}

	@Override
	public void updateTitle(int backlogItemId, String title) {
		final TypedQuery<Integer> query = em.createQuery("update BacklogItem set title = :title where backlogItemId = :backlogItemId", Integer.class);
		query.setParameter("backlogItemId", backlogItemId);
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	public void updateDescription(int backlogItemId, String description) {
		final TypedQuery<Integer> query = em.createQuery("update BacklogItem set description = :description where backlogItemId = :backlogItemId", Integer.class);
		query.setParameter("backlogItemId", backlogItemId);
		query.setParameter("description", description);
		query.executeUpdate();
	}

	@Override
	public List<BacklogItem> getBacklogForProject(int projectId) {
		final TypedQuery<BacklogItem> query = em.createQuery("from BacklogItem backlog where backlog.projectId = :projectId", BacklogItem.class);
        query.setParameter("projectId", projectId);
        return query.getResultList();
	}

	@Override
	public int getParent(int backlogItemId) {
		final TypedQuery<Integer> query = em.createQuery("select backlog.projectId from BacklogItem backlog where backlog.backlogItemId = :backlogItemId", Integer.class);
		query.setParameter("backlogItemId", backlogItemId);
		return query.getSingleResult();
	}

	@Override
	public BacklogItem getBacklogItemById(int backlogItemId) {
		final TypedQuery<BacklogItem> query = em.createQuery("from BacklogItem backlog where backlog.backlogItemId = :backlogItemId", BacklogItem.class);
		query.setParameter("backlogItemId", backlogItemId);
		return query.getSingleResult();
	}

}
