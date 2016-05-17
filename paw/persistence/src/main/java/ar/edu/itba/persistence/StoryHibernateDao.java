package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.models.Story;

public class StoryHibernateDao implements StoryDao{

	@Override
	public List<Story> getStoriesForIteration(int iterationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean storyExists(int storyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Story createStory(int iterationId, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Story getStoryById(int storyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateName(int storyId, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStory(int storyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean storyExists(int iterationId, String title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getParentId(int storyId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
