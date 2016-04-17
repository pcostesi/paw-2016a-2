package ar.edu.itba.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.IterationDetail;

public class IterationServiceImpl implements IterationService{

	@Autowired
	IterationDao iterationDao;
	
	@Override
	public IterationDetail createIteration(int projectId, Date beginDate, Date endDate) {
		return iterationDao.createIteration(projectId, beginDate, endDate);
	}

	@Override
	public boolean deleteIteration(int iterationId) {
		return iterationDao.deleteIteration(iterationId);
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		return iterationDao.getIterationById(iterationId);
	}

}
