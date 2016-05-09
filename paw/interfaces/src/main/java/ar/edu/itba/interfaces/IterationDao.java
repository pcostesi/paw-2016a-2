package ar.edu.itba.interfaces;

import java.time.LocalDate;
import java.util.List;

import ar.edu.itba.models.Iteration;

public interface IterationDao {

	public int getNextIterationNumber(final int projectId);

	public Iteration createIteration(final int projectId, final int nextIterationNumber, final LocalDate beginDate, final LocalDate endDate);

	public void deleteIteration(final int iterationId);

	public Iteration getIteration(final int projectId, final int iterationNumber);

	public Iteration getIterationById(final int iterationId);

	public boolean iterationExists(final int iterationId);

	public void updateBeginDate(final int iterationId, final LocalDate beginDate);

	public void updateEndDate(final int iterationId, final LocalDate endDate);

	public List<Iteration> getIterationsForProject(final int projectId);

	public void updateNumbersAfterDelete(final int projectId, final int number);

	public int getParentId(final int iterationId);

}