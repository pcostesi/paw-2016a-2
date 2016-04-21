package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

public class StoryServiceImpl implements StoryService{
	
	@Autowired
	StoryDao storyDao;
	
	@Autowired
	IterationDao iterationDao;

	@Override
	public Story create(Iteration iteration, String name) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (name == null) {
			throw new IllegalArgumentException("Story name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Story name needs at least 1 character");
		}
		
		if (!iterationDao.iterationExists(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		return storyDao.createStory(iteration.getIterationId(), name);
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
	public List<Story> getStoriesForIteration(Iteration iteration) {
		if (iteration == null) {
			throw new IllegalArgumentException("Iteration can't be null");
		}
		
		if (!iterationDao.iterationExists(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		return storyDao.getStoriesForIteration(iteration.getIterationId());
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
			throw new IllegalArgumentException("Story title needs at least 1 character");
		}
		
		if (!storyDao.storyExists(story.getStoryId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		storyDao.updateName(story.getStoryId(), title);
		story.setTitle(title);
			
		return story;
	}

	@Override
	public void deleteStory(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story.getStoryId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		storyDao.deleteStory(story.getStoryId());
	}

}
