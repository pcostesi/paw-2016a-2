package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public interface TaskService {

	public Task createTask(final Story story, final String name, final String description);
	
	public Task getTaskById(final int taskId);
	
	public void deleteTask(final Task task);
	
	public Task changeOwnership(final Task task, final User user);
	
	public boolean changeStatus(final int taskId, TaskStatus status);
	
	public List<Task> getTasksForStory(final Story story);

}
