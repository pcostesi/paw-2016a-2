package ar.edu.itba.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.iteration.IterationDao;
import ar.edu.itba.interfaces.iteration.IterationService;
import ar.edu.itba.models.iteration.Iteration;
import ar.edu.itba.models.iteration.IterationDetail;

@Service
public class IterationServiceImpl implements IterationService{

	@Autowired
	IterationDao iterationDao;
	
	@Override
	public IterationDetail createIteration(String projectName, Date beginDate, Date endDate) {
		return iterationDao.createIteration(projectName, beginDate, endDate);
	}

	@Override
	public boolean deleteIteration(int iterationId) {
		return iterationDao.deleteIteration(iterationId);
	}

	@Override
	public Iteration getIteration(String projectName, int iterationNumber) {
		return iterationDao.getIteration(projectName, iterationNumber);
	}

	@Override
	public Iteration getIteration(int iterationId) {
		return iterationDao.getIteration(iterationId);
	}

}
