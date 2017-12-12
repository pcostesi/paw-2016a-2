package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.dao.IterationDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Primary
@Repository
public class IterationHibernateDao implements IterationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Iteration createIteration(final Project project, final int iterationNumber, final LocalDate startDate, final LocalDate endDate) {
        try {
            final Iteration newIteration = Iteration.builder()
                    .project(project)
                    .number(iterationNumber)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            em.persist(newIteration);
            em.flush();
            return newIteration;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to create iteration");
        }
    }

    @Override
    @Transactional
    public void deleteIteration(final Iteration iteration) {
        try {
            final Query query = em.createQuery("delete from Iteration where iterationId = :iterationId");
            query.setParameter("iterationId", iteration.iterationId());
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to delete iteration");
        }
    }

    @Override
    @Transactional
    public Iteration getIteration(final Project project, final int number) {
        try {
            final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.project = :project and iteration.number = :number", Iteration.class);
            query.setParameter("project", project);
            query.setParameter("number", number);
            return query.getSingleResult();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get iteration");
        }
    }

    @Override
    @Transactional
    public Iteration getIterationById(final int iterationId) {
        try {
            final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.iterationId = :iterationId", Iteration.class);
            query.setParameter("iterationId", iterationId);
            return query.getSingleResult();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get iteration by id");
        }
    }

    @Override
    @Transactional
    public boolean iterationExists(final Iteration iteration) {
        try {
            final TypedQuery<Long> query = em.createQuery("select count(*) from Iteration iteration where iteration.iterationId = :iterationId", Long.class);
            query.setParameter("iterationId", iteration.iterationId());
            return query.getSingleResult() > 0;
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to check iteration exists");
        }
    }

    @Override
    @Transactional
    public void updateStartDate(final Iteration iteration, final LocalDate startDate) {
        try {
            final Query query = em.createQuery("update Iteration set startDate = :startDate where iterationId = :iterationId");
            query.setParameter("iterationId", iteration.iterationId());
            query.setParameter("startDate", startDate);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update iteration start date");
        }
    }

    @Override
    @Transactional
    public void updateEndDate(final Iteration iteration, final LocalDate endDate) {
        try {
            final Query query = em.createQuery("update Iteration set endDate = :endDate where iterationId = :iterationId");
            query.setParameter("iterationId", iteration.iterationId());
            query.setParameter("endDate", endDate);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to update iteration end date");
        }
    }

    @Override
    @Transactional
    public List<Iteration> getIterationsForProject(final Project project) {
        try {
            final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.project = :project order by iteration.number desc", Iteration.class);
            query.setParameter("project", project);
            return query.getResultList();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get iterations for project");
        }
    }

    @Override
    @Transactional
    public Project getParent(final Iteration iteration) {
        try {
            return iteration.project();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed to get parent for iteration");
        }
    }

    @Override
    @Transactional
    public void increaseNumberOfIterationNumbered(final Project project, final int number) {
        final Query query = em.createQuery("update Iteration set number = (number+1) where number = :number and project = :project");
        query.setParameter("number", number);
        query.setParameter("project", project);
        query.executeUpdate();

    }

    @Override
    @Transactional
    public void decreaseNumberOfIterationNumbered(final Project project, final int number) {
        try {
            final Query query = em.createQuery("update Iteration set number = (number-1) where number = :number and project = :project");
            query.setParameter("number", number);
            query.setParameter("project", project);
            query.executeUpdate();
        } catch (final Exception exception) {
            throw new IllegalStateException("Database failed while decreasing iteration number");
        }
    }

    @Override
    @Transactional
    public int getMaxNumber(final Project project) {
        final TypedQuery<Integer> query = em.createQuery("select coalesce(max(number),0) from Iteration iteration where iteration.project = :project", Integer.class);
        query.setParameter("project", project);
        return query.getSingleResult();
    }

}
