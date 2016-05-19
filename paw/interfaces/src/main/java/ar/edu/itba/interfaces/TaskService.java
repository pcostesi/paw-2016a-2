package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

public interface TaskService {

	public Task createTask(final Story story, final String name, final String description, final Status taskStatus, final User user, final Score taskScore, final Priority priority);
	
	public Task getTaskById(final int taskId);
	
	public void deleteTask(final Task task);
	
	public Task changeOwnership(final Task task, final User user);
	
	public Task changeStatus(final Task task, final Status status);
	
	public Task changePriority(final Task task, final Priority priority);
	
	public Task changeScore(final Task task, final Score score);
	
	public List<Task> getTasksForStory(final Story story);
	
	public Story getParent(final Task task);

	public Task changeTitle(final Task task, final String title);

	public Task changeDescription(final Task task, final String description);

	public boolean taskNameExists(final Story story, final String title);

}
