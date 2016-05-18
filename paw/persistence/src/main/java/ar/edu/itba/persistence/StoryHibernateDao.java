package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.PersistableStory;
import ar.edu.itba.models.Story;

@Primary
@Repository
public class StoryHibernateDao implements StoryDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<? extends Story> getStoriesForIteration(Iteration iteration) {
		return iteration.getStories();
	}

	@Override
	public boolean storyExists(Story story) {
		return em.contains(story);
	}

	@Override
	public Story createStory(Iteration iteration, String title) {
		final PersistableStory persistableStory = PersistableStory.builder()
				.title(title)
				.iterationId(iteration.iterationId())
				.build();
		em.persist(persistableStory);
		em.flush();
		return persistableStory;
	}

	@Override
	public Story getStoryById(int storyId) {
		return em.find(PersistableStory.class, storyId);
	}

	@Override
	public Story updateTitle(Story story, String title) {
		PersistableStory persistableStory = (PersistableStory) story;
		persistableStory.setTitle(title);
		return em.merge(persistableStory);
	}

	@Override
	public void deleteStory(Story story) {
		em.remove(story);
	}

	@Override
	public boolean storyExists(Iteration iteration, String title) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from PersistableStory story where story.iterationId = :iterationId and story.title = :title", Integer.class);
		query.setParameter("iterationId", iteration.iterationId());
		query.setParameter("title", title);
        return query.getSingleResult() > 0;
	}

	@Override
	public Iteration getParent(Story story) {
		return em.find(Iteration.class, story.iterationId());
	}

}
