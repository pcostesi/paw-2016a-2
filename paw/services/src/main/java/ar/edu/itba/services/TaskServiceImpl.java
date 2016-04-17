package ar.edu.itba.services;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDao taskDao;
	
	@Override
	public Task createTask(int iterationId, String title, String description) {
		return taskDao.createTask(iterationId, title, description);
	}

	@Override
	public boolean deleteTask(int taskId) {
		return taskDao.deleteTask(taskId);
	}

	@Override
	public boolean changeOwnership(int taskId, User user) {
		return taskDao.changeOwnership(taskId, user);
	}

	@Override
	public boolean changeStatus(int taskId, TaskStatus status) {
		return taskDao.changeStatus(taskId, status);
	}

}
