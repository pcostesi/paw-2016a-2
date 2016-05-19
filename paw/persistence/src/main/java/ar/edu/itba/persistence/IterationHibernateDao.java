package ar.edu.itba.persistence;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

@Primary
@Repository
public class IterationHibernateDao implements IterationDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	@Transactional
	public int getNextIterationNumber(Project project) {
		final TypedQuery<Integer> query = em.createQuery("select max(iteration.number) from Iteration iteration where iteration.project = :project", Integer.class);
        query.setParameter("project", project);
        Integer result = query.getSingleResult();
        if (result == null) {
        	return 1;
        } else {
        	return result+1;
        }
	}

	@Override
	@Transactional
	public Iteration createIteration(Project project, int nextIterationNumber, LocalDate startDate, LocalDate endDate) {
		final Iteration iteration = Iteration.builder()
				.project(project)
				.number(nextIterationNumber)
				.startDate(startDate)
				.endDate(endDate)
				.build();
		em.persist(iteration);
		em.flush();
		return iteration;
	}

	@Override
	@Transactional
	public void deleteIteration(Iteration iteration) {
		final Query query = em.createQuery("delete from Iteration where iterationId = :iterationId");
		query.setParameter("iterationId", iteration.iterationId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public Iteration getIteration(Project project, int number) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.project = :project and iteration.number = :number", Iteration.class);
		query.setParameter("project", project);
		query.setParameter("number", number);
        return query.getSingleResult();
	}

	@Override
	@Transactional
	public Iteration getIterationById(int iterationId) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.iterationId = :iterationId", Iteration.class);
		query.setParameter("iterationId", iterationId);
        return query.getSingleResult();
	}

	@Override
	@Transactional
	public boolean iterationExists(Iteration iteration) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Iteration iteration where iteration.iterationId = :iterationId", Long.class);
        query.setParameter("iterationId", iteration.iterationId());
        return query.getSingleResult() > 0;
	}

	@Override
	@Transactional
	public void updateStartDate(Iteration iteration, LocalDate startDate) {
		final Query query = em.createQuery("update Iteration set startDate = :startDate where iterationId = :iterationId");
		query.setParameter("iterationId", iteration.iterationId());
		query.setParameter("startDate", startDate);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateEndDate(Iteration iteration, LocalDate endDate) {
		final Query query = em.createQuery("update Iteration set endDate = :endDate where iterationId = :iterationId");
		query.setParameter("iterationId", iteration.iterationId());
		query.setParameter("endDate", endDate);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public List<Iteration> getIterationsForProject(Project project) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.project = :project", Iteration.class);
        query.setParameter("project", project);
        return query.getResultList();
	}

	@Override
	@Transactional
	public void updateNumbersAfterDelete(Iteration iteration, int number) {
		final Query query = em.createQuery("update Iteration set number = (number-1) where (number > :number) and (project = :project)");
		query.setParameter("number", number);
		query.setParameter("project", iteration.project());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public Project getParent(Iteration iteration) {
		return iteration.project();
	}

}
