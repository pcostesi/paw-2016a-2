package ar.edu.itba.services;

import java.time.LocalDate;
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
	public Iteration createIteration(Project project, LocalDate beginDate, LocalDate endDate) {
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
		
		if (projectDao.projectExists(project)) {
			return iterationDao.createIteration(project, iterationDao.getNextIterationNumber(project), beginDate, endDate);
		} else {
			throw new IllegalStateException("Project "+ project.name() +" doesn't exist");
		}
	}

	@Override
	public void deleteIteration(Iteration iteration) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		iterationDao.deleteIteration(iteration);
		iterationDao.updateNumbersAfterDelete(iteration, iteration.number());
	}

	@Override
	public Iteration getIteration(Project project, int iterationNumber) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (iterationNumber < 1) {
			throw new IllegalArgumentException("Iteration number has to be at least 1");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		Iteration iteration = iterationDao.getIteration(project, iterationNumber);
		
		if (iteration == null) {
			throw new IllegalStateException("Couldn't find iteration "+ iterationNumber +" in project "+ project.name());
		} else {
			return iteration;
		}
	}

	@Override
	public Iteration getIterationById(int iterationId) {
		if (iterationId < 0) {
			throw new IllegalArgumentException("Invalid iteration id");
		}

		Iteration iteration = iterationDao.getIterationById(iterationId);
		
		if (iteration == null) {
			throw new IllegalStateException("Couldn't find iteration with id "+ iterationId);
		} else {
			return iteration;
		}
	}

	@Override
	public Iteration setBeginDate(Iteration iteration, LocalDate beginDate) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (beginDate == null) {
			throw new IllegalArgumentException("Begin date can't be null");
		}
		
		if (iteration.endDate().compareTo(beginDate) < 0) {
			throw new IllegalArgumentException("Iteration can't begin after it's ending date");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (iteration.startDate().equals(beginDate)) {
			return iteration;
		}
		
		return iterationDao.updateStartDate(iteration, beginDate);
	}

	@Override
	public Iteration setEndDate(Iteration iteration, LocalDate endDate) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		if (endDate == null) {
			throw new IllegalArgumentException("End date can't be null");
		}
		
		if (iteration.startDate().compareTo(endDate) > 0) {
			throw new IllegalArgumentException("Iteration can't begin after it's ending date");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (iteration.endDate().equals(endDate)) {
			return iteration;
		}

		return iterationDao.updateEndDate(iteration, endDate);
	}

	@Override
	public List<? extends Iteration> getIterationsForProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		return iterationDao.getIterationsForProject(project);
	}

	@Override
	public Project getParent(Iteration iteration) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		return projectDao.getProjectById(iteration.projectId());
	}

}