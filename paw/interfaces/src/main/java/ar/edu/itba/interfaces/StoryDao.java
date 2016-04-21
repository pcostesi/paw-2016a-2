package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Story;

public interface StoryDao {

	public List<Story> getStoriesForIteration(final int iterationId);

	public boolean storyExists(final int storyId);

	public boolean hasTaskWithName(final String name);

	public Story createStory(final int iterationId, final String name);

	public Story getStoryById(final int storyId);

	public boolean updateName(final int storyId, final String name);

	public boolean deleteStory(final int storyId);

}
