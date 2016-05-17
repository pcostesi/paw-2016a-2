package ar.edu.itba.persistence;

import java.time.LocalDate;
import java.util.List;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;

public class IterationHibernateDao implements IterationDao{

	@Override
	public int getNextIterationNumber(int projectId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iteration createIteration(int projectId, int nextIterationNumber, LocalDate beginDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteIteration(int iterationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iteration getIteration(int projectId, int iterationNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean iterationExists(int iterationId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateBeginDate(int iterationId, LocalDate beginDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEndDate(int iterationId, LocalDate endDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Iteration> getIterationsForProject(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNumbersAfterDelete(int projectId, int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getParentId(int iterationId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
