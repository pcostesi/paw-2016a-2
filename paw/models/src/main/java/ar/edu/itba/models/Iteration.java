package ar.edu.itba.models;

import java.util.LinkedList;
import java.util.List;

public class Iteration {
	
	private IterationDetail details;
	private List<Task> tasks;
	private List<Log> logs;

	public Iteration(IterationDetail details) {
		this.details = details;
		this.tasks = new LinkedList<Task>();
		this.logs = new LinkedList<Log>();
	}
	
	public IterationDetail getDetails() {
		return details;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public List<Log> getLogs() {
		return logs;
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void addLog(Log log) {
		logs.add(log);
	}

	public String toString() {
		return details.toString();
	}
	
}
