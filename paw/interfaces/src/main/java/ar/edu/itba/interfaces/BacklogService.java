package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

public interface BacklogService {

	/**
	 * Creates a backlog item that refers a project.
	 * @param project Project to which this backlog item will belongs to.
	 * @param title Title of the backlog item.
	 * @param description A brief description of the backlog item, may be null.
	 * @return The created backlog item.
	 */
	public BacklogItem createBacklogItem(final Project project, final String title, final String description);
	
	/**
	 * Deletes a backlog item.
	 * @param backlogItem Item to delete.
	 */
	public void deleteBacklogItem(final BacklogItem backlogItem);
	
	/**
	 * Gets a list of the backlog items of a project.
	 * @param project Project to get the backlog items from.
	 * @return A list with the backlog items.
	 */
	public List<BacklogItem> getBacklogForProject(final Project project);
	
	/**
	 * Changes the title of a backlog item.
	 * @param item Item to update.
	 * @param title New title.
	 * @return The updated object.
	 */
	public BacklogItem setBacklogItemTitle(final BacklogItem item, final String title);
	
	/**
	 * Change the description of a backlog item.
	 * @param item Backlog item to update.
	 * @param description New description, may be null.
	 * @return The updated object.
	 */
	public BacklogItem setBacklogItemDescription(final BacklogItem item, final String description);

	/**
	 * Creates a backlog item from a task deleting the task and making a new backlog item with the same title.
	 * @param task The task to be backlogified.
	 * @param project The project where the backlog item will belong to.
	 * @return The new backlog item.
	 */
	public BacklogItem createBacklogItemFromTask(final Task task, final Project project);

	/**
	 * Creates a backlog item from a story deleting the story and making a new backlog item with the same title.
	 * @param story The story to be backlogified.
	 * @param project The project where the backlog item will belong to.
	 * @return The new backlog item.
	 */
	public BacklogItem createBacklogItemFromStory(final Story story, final Project project);

	/**
	 * Get the backlog item instance from its id.
	 * @param backlogId Backlog to retrieve id's.
	 * @return The matching backlog item.
	 */
	public BacklogItem getBacklogById(final int backlogId);

	/**
	 * Check the backlog title hasn't been used by another backlog item in this project.
	 * @param project Project to check in.
	 * @param title Title to verify.
	 * @return true if it has been used, false otherwise.
	 */
	public boolean titleIsUsed(final Project project, final String title);
	
}
