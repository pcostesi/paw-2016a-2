package ar.edu.itba.interfaces;

import java.util.List;
import java.util.Map;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

public interface StoryService {
	
	public Story create(Iteration iteration, String name);
	
	public Story getById(int id);
	
	public Story setName(Story story, String name);
	
	public void deleteStory(Story story);

	public Map<Story, List<Task>> getStoriesWithTasksForIteration(final Iteration iteration);
	
	public Iteration getParent(final Story story);

	public boolean storyExists(final Iteration iteration, final String title);
	
}
