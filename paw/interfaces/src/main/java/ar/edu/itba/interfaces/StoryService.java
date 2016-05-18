package ar.edu.itba.interfaces;

import java.util.List;
import java.util.Map;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

public interface StoryService {
	
	public Story create(final Iteration iteration, final String name);
	
	public Story getById(final int storyId);
	
	public Story setName(final Story story, final String name);
	
	public void deleteStory(final Story story);

	public Map<Story, List<? extends Task>> getStoriesWithTasksForIteration(final Iteration iteration);
	
	public Iteration getParent(final Story story);

	public boolean storyExists(final Iteration iteration, final String title);
	
}
