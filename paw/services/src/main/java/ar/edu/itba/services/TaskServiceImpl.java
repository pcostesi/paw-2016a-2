package ar.edu.itba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.interfaces.TaskService;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDao taskDao;
	
	@Autowired TaskServiceImpl(TaskDao taskDao){
		this.taskDao = taskDao;
	}
	
	@Override
	public Task createTask(String projectName, int iterationNumber, String title, String description) {
		return taskDao.createTask(projectName, iterationNumber, title, description);
	}
	
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

	@Override
	public Task getTask(int taskId) {
		return taskDao.getTask(taskId);
	}

}
