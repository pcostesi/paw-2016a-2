package ar.edu.itba.interfaces;

import java.util.Date;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Iteration;

public interface IterationDao {
	
	public Iteration createIteration(String projectName, Date beginDate, Date endDate);
	
	public boolean deleteIteration(int iterationId);
	
	public Iteration getIteration(String projectName, int iterationNumber);
	
	public Iteration getIteration(int iterationId);

}