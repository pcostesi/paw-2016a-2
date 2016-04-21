package ar.edu.itba.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.IterationService;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

@Service
public class IterationServiceImpl implements IterationService{

	@Autowired
	IterationDao iterationDao;
	
	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	StoryDao storyDao;

	@Override
	public Iteration createIteration(Project project, Date beginDate, Date endDate) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (beginDate == null) {
			throw new IllegalArgumentException("Begin date can't be null");
		}
		
		if (endDate == null) {
			throw new IllegalArgumentException("End date can't be null");
		}
		
		if (endDate.compareTo(beginDate) < 0) {
			throw new IllegalArgumentException("End date cannot be sooner than begin date");
		}
		
		if (projectDao.projectExists(project.getProjectId())) {
			return iterationDao.createIteration(project.getProjectId(), iterationDao.getNextIterationNumber(project.getProjectId()), beginDate, endDate);
		} else {
			throw new IllegalStateException("Project "+ project.getName()+" doesn't exist");
		}
	}

	@Override
	public void deleteIteration(Iteration iteration) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (!iterationDao.iterationExists(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (!iterationDao.deleteIteration(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration delete failed");
		}
	}

	@Override
	public Iteration getIteration(Project project, int iterationNumber) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (iterationNumber < 1) {
			throw new IllegalArgumentException("Iteration number has to be at least 1");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		Iteration iteration = iterationDao.getIteration(project.getProjectId(), iterationNumber);
		
		if (iteration == null) {
			throw new IllegalStateException("Iteration doesn't exist");
		} else {
			return iteration;
		}
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		if (iterationId < 0) {
			throw new IllegalArgumentException("Invalid iteration id");
		}
		
		if (!iterationDao.iterationExists(iterationId)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		Iteration iteration = iterationDao.getIterationById(iterationId);
		
		if (iteration == null) {
			throw new IllegalStateException("Iteration retrieval failed");
		} else {
			return iteration;
		}
	}

	@Override
	public Iteration setBeginDate(Iteration iteration, Date beginDate) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (iteration.getEndDate().compareTo(beginDate) < 0) {
			throw new IllegalArgumentException("Iteration can't begin after it's ending date");
		}
		
		if (!iterationDao.iterationExists(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (iterationDao.updateBeginDate(iteration.getIterationId(), beginDate)) {
			iteration.setBeginDate(beginDate);
			return iteration;
		} else {
			throw new IllegalStateException("Iteration update failed");
		}
	}

	@Override
	public Iteration setEndDate(Iteration iteration, Date endDate) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (iteration.getBeginDate().compareTo(endDate) > 0) {
			throw new IllegalArgumentException("Iteration can't begin before it's ending date");
		}
		
		if (!iterationDao.iterationExists(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (iterationDao.updateEndDate(iteration.getIterationId(), endDate)) {
			iteration.setEndDate(endDate);
			return iteration;
		} else {
			throw new IllegalStateException("Iteration update failed");
		}
	}

	@Override
	public List<Iteration> getIterationsForProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project.getProjectId())) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		return iterationDao.getIterationsForProject(project.getProjectId());
	}
	


}
