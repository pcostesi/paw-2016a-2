package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public class TaskJdbcDao implements TaskDao{

	@Override
	public List<Task> getTasks(Iteration iteration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTask(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTask(String title, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeOwnership(Task task, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeStatus(Task task, TaskStatus status) {
		// TODO Auto-generated method stub
		return false;
	}

}
