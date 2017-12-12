package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

import java.util.List;

public interface StoryDao {

    List<Story> getStoriesForIteration(final Iteration iteration);

    boolean storyExists(final Story story);

    Story createStory(final Iteration iteration, final String title);

    Story getStoryById(final int storyId);

    void updateTitle(final Story story, final String title);

    void deleteStory(final Story story);

    boolean storyExists(final Iteration iteration, final String title);

    Iteration getParent(final Story story);

}
