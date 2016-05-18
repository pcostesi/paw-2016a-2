package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

public interface StoryDao {

	public List<Story> getStoriesForIteration(final Iteration iteration);

	public boolean storyExists(final Story story);

	public Story createStory(final Iteration iteration, final String title);

	public Story getStoryById(final int storyId);

	public Story updateTitle(final Story story, final String title);

	public void deleteStory(final Story story);

	public boolean storyExists(final Iteration iteration, final String title);

	public Iteration getParent(final Story story);

}
