package ar.edu.itba.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.dao.IterationDao;
import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.interfaces.service.IterationService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

@Service
public class IterationServiceImpl implements IterationService{

	@Autowired
	private IterationDao iterationDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private StoryDao storyDao;
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public Iteration createIteration(Project project, LocalDate startDate, LocalDate endDate) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (startDate == null) {
			throw new IllegalArgumentException("Begin date can't be null");
		}

		if (endDate == null) {
			throw new IllegalArgumentException("End date can't be null");
		}

		if (endDate.compareTo(startDate) < 0) {
			throw new IllegalArgumentException("End date cannot be sooner than begin date");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project "+ project.name() +" doesn't exist");			
		}

		List<Iteration> iterations = iterationDao.getIterationsForProject(project);
		int iterationNumber = 1;

		for (Iteration iteration: iterations) {
			if (iteration.startDate().compareTo(startDate) < 0) {
				iterationNumber++;
			}
			if (!isValidRangeAgainstIteration(iteration, startDate, endDate)) {
				throw new IllegalStateException("Dates overlap another iteration");
			}			
		}

		int maxNumber = iterationDao.getMaxNumber(project);

		while (maxNumber >= iterationNumber) {
			iterationDao.increaseNumberOfIterationNumbered(project, maxNumber);
			maxNumber--;
		}

		return iterationDao.createIteration(project, iterationNumber, startDate, endDate);
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

		Project project = iterationDao.getParent(iteration);	
		int curNumber = iteration.number()+1;
		int maxNumber = iterationDao.getMaxNumber(project);

		while (curNumber <= maxNumber) {
			iterationDao.decreaseNumberOfIterationNumbered(project, curNumber);
			curNumber++;
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
	public Iteration setDates(Iteration iteration, LocalDate beginDate, LocalDate endDate) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}

		if (beginDate == null) {
			throw new IllegalArgumentException("Begin date can't be null");
		}
		
		if (endDate == null) {
			throw new IllegalArgumentException("End date can't be null");
		}
		
		if (endDate.compareTo(beginDate) < 0) {
			throw new IllegalArgumentException("Iteration can't begin after it's ending date");
		}

		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}

		if (iteration.startDate().equals(beginDate) && iteration.endDate().equals(endDate)) {
			return iteration;
		}
		
		Project project = iterationDao.getParent(iteration);
		
		if (isValidDateRangeInProject(project, iteration, beginDate, endDate)) {
			iterationDao.updateStartDate(iteration, beginDate);
			iterationDao.updateEndDate(iteration, endDate);
			return iterationDao.getIterationById(iteration.iterationId());
		} else {
			throw new IllegalStateException("Overlaps another iteration");			
		}
	}

	@Override
	public List<Iteration> getIterationsForProject(Project project) {
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

		return iterationDao.getParent(iteration);
	}

	public boolean isValidDateRangeInProject(Project project, Iteration iterationToExclude, LocalDate startDate, LocalDate endDate) {
		if (startDate == null) {
			throw new IllegalArgumentException("Begin date can't be null");
		}

		if (endDate == null) {
			throw new IllegalArgumentException("End date can't be null");
		}

		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}

		List<Iteration> iterations = iterationDao.getIterationsForProject(project);
		iterations.remove(iterationToExclude);
		
		for(Iteration iteration: iterations) {
			if (!isValidRangeAgainstIteration(iteration, startDate, endDate)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isValidRangeAgainstIteration(Iteration iteration, LocalDate startDate, LocalDate endDate) {
		if (startDate.compareTo(iteration.startDate()) < 0 && endDate.compareTo(iteration.startDate()) <= 0) {
			return true;
		}
		if (startDate.compareTo(iteration.endDate()) >= 0 && endDate.compareTo(iteration.endDate()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Iteration createIteration(Project project, LocalDate startDate, LocalDate endDate,
			int inheritIterationNumber) {
		if (startDate == null) {
			throw new IllegalArgumentException("Begin date can't be null");
		}

		if (endDate == null) {
			throw new IllegalArgumentException("End date can't be null");
		}

		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}		
		
		Iteration oldIteration = iterationDao.getIteration(project, inheritIterationNumber);
		
		if (oldIteration == null) {
			throw new IllegalStateException("Old iteration doesn't exist");
		}
		
		List<Story> oldStories = storyDao.getStoriesForIteration(oldIteration);
		Iteration iteration = createIteration(project, startDate, endDate);
		
		for (Story oldStory: oldStories) {
			List<Task> unfinishedTasks = taskDao.getUnfinishedTasks(oldStory);
			if (!unfinishedTasks.isEmpty()) {
				Story inheritedStory = storyDao.createStory(iteration, oldStory.title());
				for (Task unfinishedTask: unfinishedTasks) {
					taskDao.cloneTaskToStory(unfinishedTask, inheritedStory);			
				}
			}
		}
		
		return iteration;
	}

	@Override
	public Integer getLastFinishedIterationNumber(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (!projectDao.projectExists(project)) {
			throw new IllegalStateException("Project doesn't exist");
		}
		
		List<Iteration> iterations = iterationDao.getIterationsForProject(project);
		
		int itNumber = 0;
		
		for (Iteration iteration: iterations) {
			if (iteration.status() == Status.COMPLETED && iteration.number() > itNumber) {
				itNumber = iteration.number();
			}
		}
		
		return itNumber == 0 ? null : itNumber;
	}
}