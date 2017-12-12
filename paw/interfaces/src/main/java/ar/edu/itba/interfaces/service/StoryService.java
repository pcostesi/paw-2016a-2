package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

import java.util.List;
import java.util.Map;

public interface StoryService {

    /**
     * Create and persist a story instance
     *
     * @param iteration Iteration where the story will belong to.
     * @param title     Story title.
     * @return The persisted instance.
     */
    Story create(final Iteration iteration, final String title);

    /**
     * Get an story by its id.
     *
     * @param storyId Story id.
     * @return The story instance.
     */
    Story getById(final int storyId);

    /**
     * Update the story title.
     *
     * @param story Story to update title to.
     * @param title The new title.
     * @return
     */
    Story setName(final Story story, final String title);

    /**
     * Delete story instance.
     *
     * @param story Story instance to delete.
     */
    void deleteStory(final Story story);

    /**
     * Get a map of stories with all their tasks.
     *
     * @param iteration Iteration to get stories from.
     * @return A map of stories with all their tasks.
     */
    Map<Story, List<Task>> getStoriesWithTasksForIteration(final Iteration iteration);

    /**
     * Get parent iteration.
     *
     * @param story To get the iteration from.
     * @return Parent iteration instance.
     */
    Iteration getParent(final Story story);

    /**
     * Check if a story with the same title exist in the iteration.
     *
     * @param iteration Iteration to check in.
     * @param title     Title to check.
     * @return True if it has been used, false otherwise.
     */
    boolean storyExists(final Iteration iteration, final String title);

}
