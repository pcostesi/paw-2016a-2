package ar.edu.itba.interfaces.service;

import java.time.LocalDate;
import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public interface IterationService {

	public Iteration createIteration(final Project project, final LocalDate beginDate, final LocalDate endDate);
	
	public Iteration createIteration(final Project project, final LocalDate beginDate, final LocalDate endDate, final int inheritIterationNumber);
	
	public void deleteIteration(final Iteration iteration);
	
	public Iteration getIteration(final Project project, int iterationNumber);
	
	public Iteration getIterationById(int iterationId);
	
	public Iteration setBeginDate(final Iteration iteration, final LocalDate beginDate);
	
	public Iteration setEndDate(final Iteration iteration, final LocalDate endDate);

	public List<Iteration> getIterationsForProject(final Project project);
	
	public Project getParent(final Iteration iteration);

	boolean isValidDateRangeInProject(final Project project, final LocalDate startDate, final LocalDate endDate);

	public Integer getLastFinishedIterationNumber(final Project project);
	
}
