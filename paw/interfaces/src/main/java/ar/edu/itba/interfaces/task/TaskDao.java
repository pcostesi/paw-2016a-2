package ar.edu.itba.interfaces.task;

import ar.edu.itba.models.task.Task;
import ar.edu.itba.models.task.TaskStatus;
import ar.edu.itba.models.user.User;

public interface TaskDao {
	
	public Task createTask(String projectName, int iterationNumber, String title, String description);
	
	public Task createTask(int iterationId, String title, String description);
	
	public Task getTask(int taskId);
	
	public boolean deleteTask(int taskId);
	
	public boolean changeOwnership(int taskId, User user);
	
	public boolean changeStatus(int taskId, TaskStatus status);
	
}
