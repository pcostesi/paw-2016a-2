package ar.edu.itba.interfaces;

import java.util.Date;
import java.util.List;

import ar.edu.itba.models.Iteration;

public interface IterationDao {

	int getNextIterationNumber(int projectId);

	Iteration createIteration(int projectId, Object nextIterationNumber, Date beginDate, Date endDate);

	boolean deleteIteration(int iterationId);

	Iteration getIteration(int projectId, int iterationNumber);

	Iteration getIterationById(int iterationId);

	boolean iterationExists(int iterationId);

	boolean updateBeginDate(int iterationId, Date beginDate);

	boolean updateEndDate(int iterationId, Date endDate);

	List<Iteration> getIterationsForProject(int projectId);

}