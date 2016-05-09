package ar.edu.itba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.ImmutableTask;
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

	@Autowired TaskServiceImpl(TaskDao newTaskDao, StoryDao newStoryDao){
		this.taskDao = newTaskDao;
		this.storyDao = newStoryDao;
	}
	

	@Override
	public Task createTask(Story story, String title, String description, TaskStatus status, User user, TaskScore score) {
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
		
		if (description == null) {
			throw new IllegalArgumentException("Description can't be null");
		}
		
		if (description.length() == 0) {
			throw new IllegalArgumentException("Description needs can't be empty");
		}
		
		if (description.length() > 500) {
			throw new IllegalArgumentException("Description can't be longer than 500 characters");
		}
		
		if (!storyDao.storyExists(story.storyId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		if (taskDao.taskExists(story.storyId(), title)) {
			throw new IllegalStateException("Task with name "+ title +" already exists in this story");
		}
		
		return taskDao.createTask(story.storyId(), title, description, status, user, score);
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
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.deleteTask(task.taskId());
	}

	@Override
	public Task changeOwnership(Task task, User user) {
		
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateOwner(task.taskId(), user != null ? user.username() : null);
		return ImmutableTask.copyOf(task)
				.withOwner(Optional.ofNullable(user));
	}

	@Override
	public Task changeStatus(Task task, TaskStatus status) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (status == null) {
			throw new IllegalArgumentException("Status can't be null");
		}
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateStatus(task.taskId(), status.getValue());
		return ImmutableTask.copyOf(task)
				.withStatus(status);
	}

	@Override
	public List<Task> getTasksForStory(Story story) {
		if (story == null) {
			throw new IllegalArgumentException("Story can't be null");
		}
		
		if (!storyDao.storyExists(story.storyId())) {
			throw new IllegalStateException("Story doesn't exist");
		}
		
		return taskDao.getTasksForStory(story.storyId());
	}
	
	@Override
	public Story getParent(Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		int parentId = taskDao.getParentId(task.taskId());
		
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
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updatePriority(task.taskId(), priority.getValue());
		return ImmutableTask.copyOf(task).withPriority(priority);
	}

	@Override
	public Task changeScore(Task task, TaskScore score) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
		}
		
		if (score == null) {
			throw new IllegalArgumentException("Score can't be null");
		}
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateScore(task.taskId(), score.getValue());
		return ImmutableTask.copyOf(task)
				.withScore(score);
	}

	@Override
	public void changeTitle(Task task, String title) {
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
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateTitle(task.taskId(), title);
	}

	@Override
	public void changeDescription(Task task, String description) {
		if (task == null) {
			throw new IllegalArgumentException("Task can't be null");
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
		
		if (!taskDao.taskExists(task.taskId())) {
			throw new IllegalStateException("Task doesn't exist");
		}
		
		taskDao.updateDescription(task.taskId(), description);
	}

}
