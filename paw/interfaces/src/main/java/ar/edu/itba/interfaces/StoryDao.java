package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Story;

public interface StoryDao {

	List<Story> getStoriesForIteration(int iterationId);

	boolean storyExists(int storyId);

	boolean hasTaskWithName(String name);

}
