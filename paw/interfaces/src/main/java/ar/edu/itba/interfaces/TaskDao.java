package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public interface TaskDao {

	public List<Task> getTasksForStory(final int storyId);

	public boolean taskExists(final int taskId);
	
	public boolean taskExists(final int storyId, final String title);

	public void updateStatus(final int taskId, final int value);

	public void updateOwner(final int taskId, final String username);

	public void deleteTask(final int taskId);

	public Task getTaskById(final int taskId);

	public Task createTask(final int storyId, final String name, final String description, final TaskStatus status, final User user, final TaskScore score);

	public void updatePriority(final int taskId, final int value);

	public void updateScore(final int taskId, final int value);

	public int getParentId(final int taskId);

	public void updateTitle(final int taskId, final String title);

	public void updateDescription(final int taskId, final String description);
	
}
