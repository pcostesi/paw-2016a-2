package ar.edu.itba.interfaces;

import java.util.Date;
import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;

public interface IterationService {

	public Iteration createIteration(final Project project, Date beginDate, Date endDate);
	
	public void deleteIteration(final Iteration iteration);
	
	public Iteration getIteration(final Project project, int iterationNumber);
	
	public Iteration getIterationById(int iterationId);
	
	public Iteration setBeginDate(final Iteration iteration, final Date beginDate);
	
	public Iteration setEndDate(final Iteration iteration, final Date endDate);
	
	public List<Story> getStoriesForIteration(final Iteration iteration);
	
}
