package ar.edu.itba.interfaces;

import java.util.Date;
import java.util.List;

import ar.edu.itba.models.Iteration;

public interface IterationDao {

	public int getNextIterationNumber(final int projectId);

	public Iteration createIteration(final int projectId, final int nextIterationNumber, final Date beginDate, final Date endDate);

	public boolean deleteIteration(final int iterationId);

	public Iteration getIteration(final int projectId, final int iterationNumber);

	public Iteration getIterationById(final int iterationId);

	public boolean iterationExists(final int iterationId);

	public boolean updateBeginDate(final int iterationId, final Date beginDate);

	public boolean updateEndDate(final int iterationId, final Date endDate);

	public List<Iteration> getIterationsForProject(final int projectId);

}