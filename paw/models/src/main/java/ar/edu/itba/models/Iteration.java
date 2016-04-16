package ar.edu.itba.models;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Iteration {
	
	private Date startDate;
	private Date endDate;
	private Set<Task> tasks;
	private Set<Log> logs;

	public Iteration(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.tasks = new TreeSet<Task>();
		this.logs = new TreeSet<Log>();
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public Set<Task> getTasks() {
		return tasks;
	}
	
	public Set<Log> getLogs() {
		return logs;
	}
	
	public void createTask(String title, String description) {
		Task task = new Task(title, description);
		tasks.add(task);
	}
	
	public void deleteTask(Task task){
		tasks.remove(task);
	}

}
