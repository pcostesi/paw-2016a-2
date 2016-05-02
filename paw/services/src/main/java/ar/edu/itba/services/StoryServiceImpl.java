package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

@Service
public class StoryServiceImpl implements StoryService{
	
	@Autowired
	StoryDao storyDao;
	
	@Autowired
	IterationDao iterationDao;

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
		
		if (!iterationDao.iterationExists(iteration.getIterationId())) {
			throw new IllegalStateException("Iteration doesn't exist");
		}
		
		if (storyDao.storyExists(iteration.getIterationId(), title)) {
			throw new IllegalStateException("There is another story with this title in this iteration");
		}
		
		return storyDao.createStory(iteration.getIterationId(), title);
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
			throw new IllegalArgumentException("Story title can't be empty");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Story title can't be longer than 100 characters");
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
	
	@Override
	public Iteration getParent(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story.getStoryId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		int parentId = storyDao.getParentId(story.getStoryId());
		
		return iterationDao.getIterationById(parentId);	
	}

}