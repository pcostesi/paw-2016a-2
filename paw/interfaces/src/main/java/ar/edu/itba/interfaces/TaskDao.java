package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Task;

public interface TaskDao {

	List<Task> getTasksForStory(int storyId);

	boolean updateStatus(int taskId, int value);

	boolean taskExists(int taskId);

	boolean updateOwner(int taskId, String username);

	boolean deleteTask(int taskId);

	Task getTaskById(int taskId);

	Task createTask(int storyId, String name, String description);
	
}
