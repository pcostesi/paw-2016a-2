package ar.edu.itba.persistence;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;

public class IterationHibernateDao implements IterationDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public int getNextIterationNumber(int projectId) {
		final TypedQuery<Integer> query = em.createQuery("select max(number) from Iteration iteration where iteration.projectId = :projectId", Integer.class);
        query.setParameter("projectId", projectId);
        return query.getSingleResult();
	}

	@Override
	public Iteration createIteration(int projectId, int nextIterationNumber, LocalDate startDate, LocalDate endDate) {
		final Iteration iteration = Iteration.builder()
				.projectId(projectId)
				.number(nextIterationNumber)
				.startDate(startDate)
				.endDate(endDate)
				.build();
		em.persist(iteration);
		em.flush();
		return iteration;
	}

	@Override
	public void deleteIteration(int iterationId) {
		final TypedQuery<Integer> query = em.createQuery("delete from Iteration where iterationId = :iterationId", Integer.class);
		query.setParameter("iterationId", iterationId);
		query.executeUpdate();
	}

	@Override
	public Iteration getIteration(int projectId, int number) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.projectId = :projectId and iteration.number = :number", Iteration.class);
		query.setParameter("projectId", projectId);
		query.setParameter("number", number);
        return query.getSingleResult();
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.iterationId = :iterationId", Iteration.class);
		query.setParameter("iterationId", iterationId);
        return query.getSingleResult();
	}

	@Override
	public boolean iterationExists(int iterationId) {
		final TypedQuery<Integer> query = em.createQuery("select count(*) from Iteration iteration where iteration.iterationId = :iterationId", Integer.class);
        query.setParameter("iterationId", iterationId);
        return query.getSingleResult() > 0;
	}

	@Override
	public void updateBeginDate(int iterationId, LocalDate startDate) {
		final TypedQuery<Integer> query = em.createQuery("update Iteration set startDate = :startDate where iterationId = :iterationId", Integer.class);
		query.setParameter("iterationId", iterationId);
		query.setParameter("startDate", startDate);
		query.executeUpdate();
	}

	@Override
	public void updateEndDate(int iterationId, LocalDate endDate) {
		final TypedQuery<Integer> query = em.createQuery("update Iteration set endDate = :endDate where iterationId = :iterationId", Integer.class);
		query.setParameter("iterationId", iterationId);
		query.setParameter("endDate", endDate);
		query.executeUpdate();
	}

	@Override
	public List<Iteration> getIterationsForProject(int projectId) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.projectId = :projectId", Iteration.class);
        query.setParameter("projectId", projectId);
        return query.getResultList();
	}

	@Override
	public void updateNumbersAfterDelete(int projectId, int number) {
		final TypedQuery<Integer> query = em.createQuery("update Iteration set number = (:number - 1) where number > :number and projectId = :projectId", Integer.class);
		query.setParameter("number", number);
		query.setParameter("projectId", projectId);
		query.executeUpdate();
	}

	@Override
	public int getParentId(int iterationId) {
		final TypedQuery<Integer> query = em.createQuery("select iteration.projectId from Iteration iteration where iteration.iterationId = :iterationId", Integer.class);
		query.setParameter("iterationId", iterationId);
		return query.getSingleResult();
	}

}
