package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskPriority;
import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private StoryDao storyDao;

	@Override
	public Task createTask(Story story, String title, String description) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (title == null) {
			throw new IllegalArgumentException("Task title can't be null");
		}
		
		if (title.length() == 0) {
			throw new IllegalArgumentException("Task title can't be empty");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Task title can't be longer than 100 characters");
		}
		
		if (description == null) {
			throw new IllegalArgumentException("Description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Description needs can't be empty");
		}
		
		if (description.length() > 500) {
			throw new IllegalArgumentException("Description can't be longer than 500 characters");
		}
		
		if (!storyDao.storyExists(story.getStoryId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		if (taskDao.taskExists(story.getStoryId(), title)) {
			throw new IllegalStateException("Task with name "+ title +" already exists in this story");
		}
		
		return taskDao.createTask(story.getStoryId(), title, description);
	}

	@Override
	public Task getTaskById(int taskId) {
		if (taskId < 0) {
			throw new IllegalArgumentException("Invalid task id");
		}
		
		Task task = taskDao.getTaskById(taskId);
		
		if (task == null) {
			throw new IllegalStateException("Task doesn't exist");
		} else {
			return task;
		}
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
			taskDao.updateOwner(task.getTaskId(), user.username());
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
			throw new IllegalArgumentException("Status can't be null");
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
	
	@Override
	public Story getParent(Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task.getTaskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		int parentId = taskDao.getParentId(task.getTaskId());
		
		return storyDao.getStoryById(parentId);
	}

	@Override
	public Task changePriority(Task task, TaskPriority priority) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (priority == null) {
			throw new IllegalArgumentException("Priority can't be null");
		}
		
		if (!taskDao.taskExists(task.getTaskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updatePriority(task.getTaskId(), priority.getValue());
		task.setPriority(priority);
		
		return task;
	}

	@Override
	public Task changeScore(Task task, TaskScore score) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (score == null) {
			throw new IllegalArgumentException("Score can't be null");
		}
		
		if (!taskDao.taskExists(task.getTaskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateScore(task.getTaskId(), score.getValue());
		task.setScore(score);
		
		return task;
	}

}
