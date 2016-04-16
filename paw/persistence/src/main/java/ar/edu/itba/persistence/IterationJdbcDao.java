package ar.edu.itba.persistence;

import java.util.Date;
import java.util.List;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

public class IterationJdbcDao implements IterationDao {

	@Override
	public List<Iteration> getIterations(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iteration createIteration(Project project, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
