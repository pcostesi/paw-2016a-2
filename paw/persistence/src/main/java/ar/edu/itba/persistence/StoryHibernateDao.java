package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.models.Story;

public class StoryHibernateDao implements StoryDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Story> getStoriesForIteration(int iterationId) {
		final TypedQuery<Story> query = em.createQuery("from Story story where story.iterationId = :iterationId", Story.class);
        query.setParameter("iterationId", iterationId);
        return query.getResultList();
	}

	@Override
	public boolean storyExists(int storyId) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Story story where story.storyId = :storyId", Integer.class);
        query.setParameter("storyId", storyId);
        return query.getSingleResult() > 0;
	}

	@Override
	public Story createStory(int iterationId, String title) {
		final Story story = Story.builder()
				.title(title)
				.iterationId(iterationId)
				.build();
		em.persist(story);
		em.flush();
		return story;
	}

	@Override
	public Story getStoryById(int storyId) {
		final TypedQuery<Story> query = em.createQuery("from Story story where story.storyId = :storyId", Story.class);
        query.setParameter("storyId", storyId);
        return query.getSingleResult();
	}

	@Override
	public void updateName(int storyId, String title) {
		final TypedQuery<Integer> query = em.createQuery("update Story set title = :title where storyId = :storyId", Integer.class);
		query.setParameter("storyId", storyId);
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	public void deleteStory(int storyId) {
		final TypedQuery<Integer> query = em.createQuery("delete from Story where storyId = :storyId", Integer.class);
		query.setParameter("storyId", storyId);
		query.executeUpdate();
	}

	@Override
	public boolean storyExists(int iterationId, String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Story story where story.iterationId = :iterationId and story.title = :title", Integer.class);
		query.setParameter("iterationId", iterationId);
		query.setParameter("title", title);
        return query.getSingleResult() > 0;
	}

	@Override
	public int getParentId(int storyId) {
		final TypedQuery<Integer> query = em.createQuery("select story.iterationId from Story story where story.storyId = :storyId", Integer.class);
		query.setParameter("storyId", storyId);
		return query.getSingleResult();
	}

}
