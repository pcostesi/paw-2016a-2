package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private StoryDao storyDao;

	@Override
	public Task createTask(Story story, String name, String description) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (name == null) {
			throw new IllegalArgumentException("Project name can't be null");
		}
		
		if (name.length() == 0) {
			throw new IllegalArgumentException("Project name needs at least 1 character");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Description needs at least 1 character");
		}
		
		if (!storyDao.storyExists(story.getStoryId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		if (storyDao.hasTaskWithName(name)) {
			throw new IllegalStateException("Task with name "+ name +" already exists");
		}
		
		return taskDao.createTask(story.getStoryId(), name, description);
	}

	@Override
	public Task getTaskById(int taskId) {
		if (taskId < 0) {
			throw new IllegalArgumentException("Invalid task id");
		}
		
		if (!taskDao.taskExists(taskId)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		return taskDao.getTaskById(taskId);
	}

	@Override
	public void deleteTask(Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task.getTaskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.deleteTask(task.getTaskId());
	}

	@Override
	public Task changeOwnership(Task task, User user) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task.getTaskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (user == null) {
			taskDao.updateOwner(task.getTaskId(), null);
			task.setOwner(null);
		} else {
			taskDao.updateOwner(task.getTaskId(), user.getUsername());
			task.setOwner(user);
		}
		
		return task;
	}

	@Override
	public Task changeStatus(Task task, TaskStatus status) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (status == null) {
			throw new IllegalArgumentException("tatus can't be null");
		}
		
		if (!taskDao.taskExists(task.getTaskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateStatus(task.getTaskId(), status.getValue());
		task.setStatus(status);
		
		return task;
	}

	@Override
	public List<Task> getTasksForStory(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story.getStoryId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		return taskDao.getTasksForStory(story.getStoryId());
	}

}
