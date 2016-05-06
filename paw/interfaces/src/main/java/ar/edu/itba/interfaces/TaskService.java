package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskPriority;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public interface TaskService {

	public Task createTask(final Story story, final String name, final String description, final TaskStatus taskStatus, final User user, final TaskScore taskScore);
	
	public Task getTaskById(final int taskId);
	
	public void deleteTask(final Task task);
	
	public Task changeOwnership(final Task task, final User user);
	
	public Task changeStatus(final Task task, final TaskStatus status);
	
	public Task changePriority(final Task task, final TaskPriority priority);
	
	public Task changeScore(final Task task, final TaskScore score);
	
	public List<Task> getTasksForStory(final Story story);
	
	public Story getParent(final Task task);

	public void changeTitle(final Task task, final String title);

	public void changeDescription(final Task task, final String description);

}
