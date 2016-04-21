package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Task;

public interface TaskDao {

	public List<Task> getTasksForStory(final int storyId);

	public boolean taskExists(final int taskId);

	public void updateStatus(final int taskId, final int value);

	public void updateOwner(final int taskId, final String username);

	public void deleteTask(final int taskId);

	public Task getTaskById(final int taskId);

	public Task createTask(final int storyId, final String name, final String description);
	
}
