package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

public interface StoryService {
	
	public Story create(Iteration iteration, String name);
	
	public Story getById(int id);
	
	public Story setName(Story story, String name);
	
	public void deleteStory(Story story);

	public List<Story> getStoriesForIteration(final Iteration iteration);
	
	public Iteration getParent(final Story story);
	
}
