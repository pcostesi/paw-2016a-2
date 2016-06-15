package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

@Primary
@Repository
public class StoryHibernateDao implements StoryDao{

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public List<Story> getStoriesForIteration(Iteration iteration) {
		try {
			final TypedQuery<Story> query = em.createQuery("from Story story where story.iteration = :iteration", Story.class);
			query.setParameter("iteration", iteration);
			return query.getResultList();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get stories for iteration");
		}	
	}

	@Override
	@Transactional
	public boolean storyExists(Story story) {
		try{
			final TypedQuery<Long> query = em.createQuery("select count(*) from Story story where story.storyId = :storyId", Long.class);
			query.setParameter("storyId", story.storyId());
			return query.getSingleResult() > 0;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to check story exists");
		}	
	}

	@Override
	@Transactional
	public Story createStory(Iteration iteration, String title) {
		try{
			final Story persistableStory = Story.builder()
					.title(title)
					.iteration(iteration)
					.build();
			em.persist(persistableStory);
			em.flush();
			return persistableStory;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to create story");
		}	
	}

	@Override
	@Transactional
	public Story getStoryById(int storyId) {
		try{
			final TypedQuery<Story> query = em.createQuery("from Story story where story.storyId = :storyId", Story.class);
			query.setParameter("storyId", storyId);
			return query.getSingleResult();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get story by id");
		}	
	}

	@Override
	@Transactional
	public void updateTitle(Story story, String title) {
		try{
			final Query query = em.createQuery("update Story set title = :title where storyId = :storyId");
			query.setParameter("storyId", story.storyId());
			query.setParameter("title", title);
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to update story title");
		}	
	}

	@Override
	@Transactional
	public void deleteStory(Story story) {
		try {
			final Query query = em.createQuery("delete from Story where storyId = :storyId");
			query.setParameter("storyId", story.storyId());
			query.executeUpdate();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to delete story");
		}	
	}

	@Override
	@Transactional
	public boolean storyExists(Iteration iteration, String title) {
		try {
			final TypedQuery<Long> query = em.createQuery("select count(*) from Story story where story.iteration = :iteration and story.title = :title", Long.class);
			query.setParameter("iteration", iteration);
			query.setParameter("title", title);
			return query.getSingleResult() > 0;
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to check story exists");
		}	
	}

	@Override
	@Transactional
	public Iteration getParent(Story story) {
		try {
			return story.iteration();
		} catch (Exception exception) {
			throw new IllegalStateException("Database failed to get parent for story");
		}	
	}

}
