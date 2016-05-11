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
import ar.edu.itba.models.ImmutableStory;
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
	/*default*/ StoryServiceImpl(StoryDao storyDao2, IterationDao iterationDao2, TaskDao taskDao2) {
		this.storyDao = storyDao2;
		this.iterationDao = iterationDao2;
		this.taskDao = taskDao2;
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
		
		if (!iterationDao.iterationExists(iteration.iterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (storyDao.storyExists(iteration.iterationId(), title)) {
			throw new IllegalStateException("There is another story with this title in this iteration");
		}
		
		return storyDao.createStory(iteration.iterationId(), title);
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
	public Map<Story, List<Task>> getStoriesWithTasksForIteration(Iteration iteration) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (!iterationDao.iterationExists(iteration.iterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		Map<Story, List<Task>> result = new HashMap<Story, List<Task>>();
		List<Story> stories = storyDao.getStoriesForIteration(iteration.iterationId());
		for (Story story: stories) {
			result.put(story, taskDao.getTasksForStory(story.storyId()));
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
		
		if (!storyDao.storyExists(story.storyId())) {
			throw new IllegalStateException("Story doesn't exist");
		}

		if (story.title().equals(title)) {
			return ImmutableStory.copyOf(story);
		}

		int parentId = storyDao.getParentId(story.storyId());
		
		if (storyDao.storyExists(parentId, title)) {
			throw new IllegalStateException("There is another story with this title in this iteration");
		}

		storyDao.updateName(story.storyId(), title);
		return ImmutableStory.copyOf(story).withTitle(title);
	}

	@Override
	public void deleteStory(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story.storyId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		storyDao.deleteStory(story.storyId());
	}
	
	@Override
	public Iteration getParent(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story.storyId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		int parentId = storyDao.getParentId(story.storyId());
		
		return iterationDao.getIterationById(parentId);	
	}

	@Override
	public boolean storyExists(Iteration iteration, String title) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration cant' be null");
		}
		
		if (!iterationDao.iterationExists(iteration.iterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		return storyDao.storyExists(iteration.iterationId(), title);
	}

}