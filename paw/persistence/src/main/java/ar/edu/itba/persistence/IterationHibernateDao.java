package ar.edu.itba.persistence;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.PersistableIteration;
import ar.edu.itba.models.Project;

public class IterationHibernateDao implements IterationDao{

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public int getNextIterationNumber(Project project) {
		final TypedQuery<Integer> query = em.createQuery("select max(number) from Iteration iteration where iteration.projectId = :projectId", Integer.class);
        query.setParameter("projectId", project.projectId());
        return query.getSingleResult();
	}

	@Override
	public Iteration createIteration(Project project, int nextIterationNumber, LocalDate startDate, LocalDate endDate) {
		final PersistableIteration persistableIteration = PersistableIteration.builder()
				.projectId(project.projectId())
				.number(nextIterationNumber)
				.startDate(startDate)
				.endDate(endDate)
				.build();
		em.persist(persistableIteration);
		em.flush();
		return persistableIteration;
	}

	@Override
	public void deleteIteration(Iteration iteration) {
		PersistableIteration persistableIteration = (PersistableIteration) iteration;
		em.remove(persistableIteration);
	}

	@Override
	public Iteration getIteration(Project project, int number) {
		final TypedQuery<Iteration> query = em.createQuery("from Iteration iteration where iteration.projectId = :projectId and iteration.number = :number", Iteration.class);
		query.setParameter("projectId", project.projectId());
		query.setParameter("number", number);
        return query.getSingleResult();
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		return em.find(Iteration.class, iterationId);
	}

	@Override
	public boolean iterationExists(Iteration iteration) {
		return em.contains(iteration);
	}

	@Override
	public Iteration updateStartDate(Iteration iteration, LocalDate startDate) {
		PersistableIteration persistableIteration = (PersistableIteration) iteration;
		persistableIteration.setStartDate(startDate);
		return em.merge(persistableIteration);
	}

	@Override
	public Iteration updateEndDate(Iteration iteration, LocalDate endDate) {
		PersistableIteration persistableIteration = (PersistableIteration) iteration;
		persistableIteration.setEndDate(endDate);
		return em.merge(persistableIteration);
	}

	@Override
	public List<Iteration> getIterationsForProject(Project project) {
		return project.geIterations();
	}

	@Override
	public void updateNumbersAfterDelete(Iteration iteration, int number) {
		final TypedQuery<Integer> query = em.createQuery("update Iteration set number = (:number - 1) where number > :number and projectId = :projectId", Integer.class);
		query.setParameter("number", number);
		query.setParameter("projectId", iteration.projectId());
		query.executeUpdate();
	}

	@Override
	public Project getParent(Iteration iteration) {
		return em.find(Project.class, iteration.projectId());
	}

}
