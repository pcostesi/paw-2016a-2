package ar.edu.itba.interfaces;

import java.util.Date;
import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public interface IterationDao {
	
	public List<Iteration> getIterations(Project project);
	
	public Iteration createIteration(Project project, Date beginDate, Date endDate);

}
