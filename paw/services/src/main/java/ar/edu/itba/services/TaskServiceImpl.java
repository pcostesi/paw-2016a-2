package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Iteration;
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
	public Task createTask(Story story, String title, String description, Status status, User user, Score score, Priority priority) {
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
		
		if (user != null && user.username().length() == 0) {
			throw new IllegalArgumentException("Task owner can't be empty");
		}
		
		if (user != null && user.username().length() > 100) {
			throw new IllegalArgumentException("Task owner can't be longer than 100 characters");
		}
		
		if (description != null && description.length() == 0) {
			throw new IllegalArgumentException("Description can't be empty");
		}
		
		if (description != null && description.length() > 500) {
			throw new IllegalArgumentException("Description can't be longer than 500 characters");
		}
		if (!storyDao.storyExists(story)) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		if (taskDao.taskExists(story, title)) {
			throw new IllegalStateException("Task with name "+ title +" already exists in this story");
		}
		
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't add tasks to a finished iteration");
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
		
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't delete a task from a finished iteration");
		}
		
		taskDao.deleteTask(task);
	}

	@Override
	public Task changeOwnership(Task task, User user) {		
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}		
		
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't edit a task from a finished iteration");
		}
		
		taskDao.updateOwner(task, user);
		
		return taskDao.getTaskById(task.taskId());
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
		
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't edit a task from a finished iteration");
		}
		
		taskDao.updateStatus(task, status);

		return taskDao.getTaskById(task.taskId());
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
		
		return taskDao.getParent(task);
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
		
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't edit a task from a finished iteration");
		}
		
		taskDao.updatePriority(task, priority);
		return taskDao.getTaskById(task.taskId());		
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
				
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't edit a task from a finished iteration");
		}
		
		taskDao.updateScore(task, score);
		
		return taskDao.getTaskById(task.taskId());
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
		
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't edit a task from a finished iteration");
		}
		
		taskDao.updateTitle(task, title);
		
		return taskDao.getTaskById(task.taskId());
	}

	@Override
	public Task changeDescription(Task task, String description) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
			
		if (description != null && description.length() == 0) {
			throw new IllegalArgumentException("Description can't be empty");
		}
		
		if (description != null && description.length() > 500) {
			throw new IllegalArgumentException("Description can't be longer than 500 characters");
		}
		
		if (!taskDao.taskExists(task)) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		if (task.description().equals(description)) {
			return task;
		}
		
		Story story = taskDao.getParent(task);
		Iteration iteration = storyDao.getParent(story);
		
		if(iteration.status() == Status.COMPLETED) {
			throw new IllegalStateException("Can't edit a task from a finished iteration");
		}
		
		taskDao.updateDescription(task, description);
		
		return taskDao.getTaskById(task.taskId());
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
