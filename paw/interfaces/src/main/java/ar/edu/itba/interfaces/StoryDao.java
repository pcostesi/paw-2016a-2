package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Story;

public interface StoryDao {

	public List<Story> getStoriesForIteration(final int iterationId);

	public boolean storyExists(final int storyId);

	public boolean hasTaskWithName(final int storyId, final String title);

	public Story createStory(final int iterationId, final String title);

	public Story getStoryById(final int storyId);

	public void updateName(final int storyId, final String title);

	public void deleteStory(final int storyId);

}
