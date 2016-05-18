package ar.edu.itba.interfaces;

import java.time.LocalDate;
import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public interface IterationDao {

	public int getNextIterationNumber(final Project project);

	public Iteration createIteration(final Project project, final int nextIterationNumber, final LocalDate startDate, final LocalDate endDate);

	public void deleteIteration(final Iteration iteration);

	public Iteration getIteration(final Project project, final int iterationNumber);

	public Iteration getIterationById(final int iterationId);

	public boolean iterationExists(final Iteration iteration);

	public Iteration updateStartDate(final Iteration iteration, final LocalDate startDate);

	public Iteration updateEndDate(final Iteration iteration, final LocalDate endDate);

	public List<? extends Iteration> getIterationsForProject(final Project project);

	public void updateNumbersAfterDelete(final Iteration iteration, final int number);

	public Project getParent(final Iteration iteration);

}