package ar.edu.itba.interfaces;

import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public interface TaskService {

	public Task createTask(String projectName, int iterationNumber, String title, String description);
	
	public Task createTask(int iterationId, String title, String description);

	public Task getTask(int taskId);
	
	public boolean deleteTask(int taskId);
	
	public boolean changeOwnership(int taskId, User user);
	
	public boolean changeStatus(int taskId, TaskStatus status);
	
}
