package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public interface TaskDao {

	public List<Task> getTasks(Iteration iteration);
	
	public boolean deleteTask(Task task);
	
	public boolean createTask(String title, String description);
	
	public boolean changeOwnership(Task task, User user);
	
	public boolean changeStatus(Task task, TaskStatus status);
}
