package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

public interface TaskService {

	/**
	 * Create and persist a new task instance.
	 * @param story Story create the task in.
	 * @param title Title of the new task. 
	 * @param description A brief description of the task.
	 * @param taskStatus The new task status.
	 * @param owner The new task owner.
	 * @param taskScore The task estimated score.
	 * @param priority The task priority.
	 * @return The persisted instance.
	 */
	public Task createTask(final Story story, final String title, final String description, final Status taskStatus, final User owner, final Score taskScore, final Priority priority);
	
	/**
	 * Get task by its id.
	 * @param taskId Task id.
	 * @return Task instance with this id.
	 */
	public Task getTaskById(final int taskId);
	
	/** 
	 * Remove this instance from the database
	 * @param task Task to delete.
	 */
	public void deleteTask(final Task task);
	
	/**
	 * Change task owner.
	 * @param task Task to change owner to.
	 * @param user New owner.
	 * @return The modified instance of the task.
	 */
	public Task changeOwnership(final Task task, final User user);
	
	/**
	 * Update the task status.
	 * @param task The task to update.
	 * @param status New status.
	 * @return The updated instance.
	 */
	public Task changeStatus(final Task task, final Status status);
	
	/**
	 * Update the task priority.
	 * @param task Task to update.
	 * @param priority The new priority.
	 * @return The updated instance.
	 */
	public Task changePriority(final Task task, final Priority priority);
	
	/**
	 * Update the task score.
	 * @param task Task to update score to.
	 * @param score The new score.
	 * @return Task instance with the updated score.
	 */
	public Task changeScore(final Task task, final Score score);
	
	/**
	 * Get a list of tasks for a story.
	 * @param story Story to get tasks from.
	 * @return A list of the task of the story.
	 */
	public List<Task> getTasksForStory(final Story story);
	
	/**
	 * Get parent story of the task.
	 * @param task Task to get parent from.
	 * @return Parent story.
	 */
	public Story getParent(final Task task);

	/**
	 * Update task title
	 * @param task Task to update.
	 * @param title 
	 * @return
	 */
	public Task changeTitle(final Task task, final String title);

	/**
	 * Update task description.
	 * @param task Task to update.
	 * @param description New project description (can be null).
	 * @return The updated instance
	 */
	public Task changeDescription(final Task task, final String description);

	/**
	 * Check if the task title has been used already in this story.
	 * @param story Story to check in.
	 * @param title Title to check.
	 * @return True if it was used, false otherwise.
	 */
	public boolean taskNameExists(final Story story, final String title);

}
