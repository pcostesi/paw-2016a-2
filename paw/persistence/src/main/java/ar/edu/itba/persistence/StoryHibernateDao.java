package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Primary
@Repository
public class StoryHibernateDao implements StoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Story> getStoriesForIteration(final Iteration iteration) {
        try {
            final TypedQuery<Story> query = em.createQuery("from Story story where story.iteration = :iteration", Story.class);
            query.setParameter("iteration", iteration);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get stories for iteration");
        }
    }

    @Override
    @Transactional
    public boolean storyExists(final Story story) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Story story where story.storyId = :storyId", Long.class);
            query.setParameter("storyId", story.storyId());
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check story exists");
        }
    }

    @Override
    @Transactional
    public Story createStory(final Iteration iteration, final String title) {
        try {
            final Story persistableStory = Story.builder()
                    .title(title)
                    .iteration(iteration)
                    .build();
            em.persist(persistableStory);
            em.flush();
            return persistableStory;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to create story");
        }
    }

    @Override
    @Transactional
    public Story getStoryById(final int storyId) {
        try {
            final TypedQuery<Story> query = em.createQuery("from Story story where story.storyId = :storyId", Story.class);
            query.setParameter("storyId", storyId);
            return query.getSingleResult();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get story by id");
        }
    }

    @Override
    @Transactional
    public void updateTitle(final Story story, final String title) {
        try {
            final Query query = em.createQuery("update Story set title = :title where storyId = :storyId");
            query.setParameter("storyId", story.storyId());
            query.setParameter("title", title);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update story title");
        }
    }

    @Override
    @Transactional
    public void deleteStory(final Story story) {
        try {
            final Query query = em.createQuery("delete from Story where storyId = :storyId");
            query.setParameter("storyId", story.storyId());
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete story");
        }
    }

    @Override
    @Transactional
    public boolean storyExists(final Iteration iteration, final String title) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Story story where story.iteration = :iteration and story.title = :title", Long.class);
            query.setParameter("iteration", iteration);
            query.setParameter("title", title);
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check story exists");
        }
    }

    @Override
    @Transactional
    public Iteration getParent(final Story story) {
        try {
            return story.iteration();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get parent for story");
        }
    }

}
