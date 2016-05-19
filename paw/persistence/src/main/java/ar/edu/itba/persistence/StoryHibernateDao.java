package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.StoryDao;
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
		final TypedQuery<Story> query = em.createQuery("from Story story where story.iteration = :iteration", Story.class);
        query.setParameter("iteration", iteration);
        return query.getResultList();
	}

	@Override
	@Transactional
	public boolean storyExists(Story story) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Story story where story.storyId = :storyId", Long.class);
        query.setParameter("storyId", story.storyId());
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public Story createStory(Iteration iteration, String title) {
		final Story persistableStory = Story.builder()
				.title(title)
				.iteration(iteration)
				.build();
		em.persist(persistableStory);
		em.flush();
		return persistableStory;
	}

	@Override
	@Transactional
	public Story getStoryById(int storyId) {
		final TypedQuery<Story> query = em.createQuery("from Story story where story.storyId = :storyId", Story.class);
        query.setParameter("storyId", storyId);
        return query.getSingleResult();
	}

	@Override
	@Transactional
	public void updateTitle(Story story, String title) {
		final Query query = em.createQuery("update Story set title = :title where storyId = :storyId");
		query.setParameter("storyId", story.storyId());
		query.setParameter("title", title);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteStory(Story story) {
		final Query query = em.createQuery("delete from Story where storyId = :storyId");
		query.setParameter("storyId", story.storyId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public boolean storyExists(Iteration iteration, String title) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Story story where story.iteration = :iteration and story.title = :title", Long.class);
		query.setParameter("iteration", iteration);
		query.setParameter("title", title);
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public Iteration getParent(Story story) {
		return story.iteration();
	}

}
