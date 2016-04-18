package ar.edu.itba.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.IterationDetail;

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

}