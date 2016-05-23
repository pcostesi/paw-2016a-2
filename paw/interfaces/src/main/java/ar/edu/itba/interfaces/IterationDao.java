package ar.edu.itba.interfaces;

import java.time.LocalDate;
import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public interface IterationDao {

	public Iteration createIteration(final Project project, final int iterationNumber, final LocalDate startDate, final LocalDate endDate);

	public void deleteIteration(final Iteration iteration);

	public Iteration getIteration(final Project project, final int iterationNumber);

	public Iteration getIterationById(final int iterationId);

	public boolean iterationExists(final Iteration iteration);

	public void updateStartDate(final Iteration iteration, final LocalDate startDate);

	public void updateEndDate(final Iteration iteration, final LocalDate endDate);

	public List<Iteration> getIterationsForProject(final Project project);

	public void increaseNumberOfIterationNumbered(final Project project, final int number);
	
	public void decreaseNumberOfIterationNumbered(final Project project, final int number);

	public Project getParent(final Iteration iteration);

	public int getMaxNumber(final Project project);

}