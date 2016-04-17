package ar.edu.itba.interfaces;

import java.util.Date;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.IterationDetail;

public interface IterationDao {
	
	public IterationDetail createIteration(int projectId, Date beginDate, Date endDate);
	
	public boolean deleteIteration(int iterationId);
	
	public Iteration getIterationById(int iterationId);

}