package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Task;

public interface TaskDao {

	public List<Task> getTasksForStory(final int storyId);

	public boolean updateStatus(final int taskId, final int value);

	public boolean taskExists(final int taskId);

	public boolean updateOwner(final int taskId, final String username);

	public boolean deleteTask(final int taskId);

	public Task getTaskById(final int taskId);

	public Task createTask(final int storyId, final String name, final String description);
	
}
