package ar.edu.itba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Priority;
import ar.edu.itba.models.Score;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private StoryDao storyDao;

	@Autowired TaskServiceImpl(TaskDao newTaskDao, StoryDao newStoryDao){
		this.taskDao = newTaskDao;
		this.storyDao = newStoryDao;
	}	

	@Override
	public Task createTask(Story story, String title, Optional<String> description, Status status, Optional<User> user, Score score, Priority priority) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (title == null) {
			throw new IllegalArgumentException("Task title can't be null");
		}
		
		if (status == null) {
			throw new IllegalArgumentException("Task status can't be null");
		}
		
		if (score == null) {
			throw new IllegalArgumentException("Task score can't be null");
		}
		
		if (title.length() == 0) {
			throw new IllegalArgumentException("Task title can't be empty");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Task title can't be longer than 100 characters");
		}
		
		if (user.isPresent() && user.get().username().length() == 0) {
			throw new IllegalArgumentException("Task owner can't be empty");
		}
		
		if (user.isPresent() && user.get().username().length() > 100) {
			throw new IllegalArgumentException("Task owner can't be longer than 100 characters");
		}
		
		if (description.isPresent() && description.get().length() == 0) {
			throw new IllegalArgumentException("Description can't be empty");
		}
		
		if (description.isPresent() && description.get().length() > 500) {
			throw new IllegalArgumentException("Description can't be longer than 500 characters");
		}
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		if (taskDao.taskExists(story, title)) {
			throw new IllegalStateException("Task with name "+ title +" already exists in this story");
		}
		
		return taskDao.createTask(story, title, description, status, user, score, priority);
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
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.deleteTask(task);
	}

	@Override
	public Task changeOwnership(Task task, Optional<User> user) {		
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}		
		
		return taskDao.updateOwner(task, user);
		
	}

	@Override
	public Task changeStatus(Task task, Status status) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (status == null) {
			throw new IllegalArgumentException("Status can't be null");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (task.status().equals(status)) {
			return task;
		}		
		
		return taskDao.updateStatus(task, status);

	}

	@Override
	public List<Task> getTasksForStory(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		return taskDao.getTasksForStory(story);
	}
	
	@Override
	public Story getParent(Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		return storyDao.getStoryById(task.storyId());
	}

	@Override
	public Task changePriority(Task task, Priority priority) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (priority == null) {
			throw new IllegalArgumentException("Priority can't be null");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (task.priority().equals(priority)) {
			return task;
		}
		
		return taskDao.updatePriority(task, priority);
		
	}

	@Override
	public Task changeScore(Task task, Score score) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (score == null) {
			throw new IllegalArgumentException("Score can't be null");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (task.score().equals(score)) {
			return task;
		}
				
		return taskDao.updateScore(task, score);
	}

	@Override
	public Task changeTitle(Task task, String title) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
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
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (task.title().equals(title)) {
			return task;
		}
		
		if (taskDao.taskExists(taskDao.getParent(task), title)) {
			throw new IllegalStateException("Task with name "+ title +" already exists in this story");
		}		
		
		return taskDao.updateTitle(task, title);
	}

	@Override
	public Task changeDescription(Task task, Optional<String> description) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
			
		if (description.isPresent() && description.get().length() == 0) {
			throw new IllegalArgumentException("Description can't be empty");
		}
		
		if (description != null && description.get().length() > 500) {
			throw new IllegalArgumentException("Description can't be longer than 500 characters");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (task.description().equals(description)) {
			return task;
		}
		
		return taskDao.updateDescription(task, description);
	}

	@Override
	public boolean taskNameExists(Story story, String title) {
		if (story == null) {
			throw new IllegalStateException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		if (title == null) {
			throw new IllegalArgumentException("Title can't be null");
		}
		
		return taskDao.taskExists(story, title);
	}

}
