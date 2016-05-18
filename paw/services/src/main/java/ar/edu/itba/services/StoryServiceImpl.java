package ar.edu.itba.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

@Service
public class StoryServiceImpl implements StoryService{
	
	@Autowired
	StoryDao storyDao;
	
	@Autowired
	IterationDao iterationDao;
	
	@Autowired
	TaskDao taskDao;

	@Autowired
	StoryServiceImpl(StoryDao storyDao, IterationDao iterationDao, TaskDao taskDao) {
		this.storyDao = storyDao;
		this.iterationDao = iterationDao;
		this.taskDao = taskDao;
	}

	@Override
	public Story create(Iteration iteration, String title) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (title == null) {
			throw new IllegalArgumentException("Story title can't be null");
		}
		
		if (title.length() == 0) {
			throw new IllegalArgumentException("Story title can't be empty");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Story title can't be longer than 100 characters");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (storyDao.storyExists(iteration, title)) {
			throw new IllegalStateException("There is another story with this title in this iteration");
		}
		
		return storyDao.createStory(iteration, title);
	}

	@Override
	public Story getById(int storyId) {
		if (storyId < 0) {
			throw new IllegalArgumentException("Invalid story id");
		}
		
		Story story = storyDao.getStoryById(storyId);
		
		if (story == null) {
			throw new IllegalStateException("Story doesn't exist");
		} else {
			return story;
		}
	}
	
	@Override
	public Map<Story, List<? extends Task>> getStoriesWithTasksForIteration(Iteration iteration) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		Map<Story, List<? extends Task>> result = new HashMap<Story, List<? extends Task>>();
		List<? extends Story> stories = storyDao.getStoriesForIteration(iteration);
		for (Story story: stories) {
			result.put(story, taskDao.getTasksForStory(story));
		}
		return result;
	}

	@Override
	public Story setName(Story story, String title) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (title == null) {
			throw new IllegalArgumentException("Story title can't be null");
		}
		
		if (title.length() == 0) {
			throw new IllegalArgumentException("Story title can't be empty");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Story title can't be longer than 100 characters");
		}
		
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}

		if (story.title().equals(title)) {
			return story;
		}
		
		if (storyDao.storyExists(storyDao.getParent(story), title)) {
			throw new IllegalStateException("There is another story with this title in this iteration");
		}

		return storyDao.updateTitle(story, title);
	}

	@Override
	public void deleteStory(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		storyDao.deleteStory(story);
	}
	
	@Override
	public Iteration getParent(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		return iterationDao.getIterationById(story.iterationId());	
	}

	@Override
	public boolean storyExists(Iteration iteration, String title) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration cant' be null");
		}
		
		if (!iterationDao.iterationExists(iteration)) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		return storyDao.storyExists(iteration, title);
	}

}