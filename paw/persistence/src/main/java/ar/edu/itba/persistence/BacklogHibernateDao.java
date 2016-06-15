package ar.edu.itba.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.dao.BacklogDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;

@Primary
@Repository
public class BacklogHibernateDao implements BacklogDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	@Transactional
	public BacklogItem createBacklogItem(String title, String description, Project project) {
		final BacklogItem backlogItem = BacklogItem.builder()
				.title(title)
				.description(Optional.ofNullable(description))
				.project(project)
				.build();
		em.persist(backlogItem);
		em.flush();
		return backlogItem;
	}

	@Override
	@Transactional
	public boolean backlogItemExists(Project project, String title) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from BacklogItem backlog where backlog.title = :title and backlog.project = :project", Long.class);
		query.setParameter("title", title);
		query.setParameter("project", project);
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public boolean backlogItemExists(BacklogItem backlogItem) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from BacklogItem backlog where backlog.backlogItemId = :backlogItemId", Long.class);
		query.setParameter("backlogItemId", backlogItem.backlogItemId());
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public void deleteItem(BacklogItem backlogItem) {
		final Query query = em.createQuery("delete from BacklogItem where backlogItemId = :backlogItemId");
		query.setParameter("backlogItemId", backlogItem.backlogItemId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateTitle(BacklogItem backlogItem, String title) {
		final Query query = em.createQuery("update BacklogItem set title = :title where backlogItemId = :backlogItemId");
		query.setParameter("backlogItemId", backlogItem.backlogItemId());
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateDescription(BacklogItem backlogItem, String description) {
		final Query query = em.createQuery("update BacklogItem set description = :description where backlogItemId = :backlogItemId");
		query.setParameter("backlogItemId", backlogItem.backlogItemId());
		query.setParameter("description", description);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public List<BacklogItem> getBacklogForProject(Project project) {
		final TypedQuery<BacklogItem> query = em.createQuery("from BacklogItem backlog where backlog.project = :project", BacklogItem.class);
		query.setParameter("project", project);
		return query.getResultList();
	}

	@Override
	@Transactional
	public Project getParent(BacklogItem backlogItem) {
		return backlogItem.project();
	}

	@Override
	@Transactional
	public BacklogItem getBacklogItemById(int backlogItemId) {
		final TypedQuery<BacklogItem> query = em.createQuery("from BacklogItem backlog where backlog.backlogItemId = :backlogItemId", BacklogItem.class);
		query.setParameter("backlogItemId", backlogItemId);
		return query.getSingleResult();
	}

}
