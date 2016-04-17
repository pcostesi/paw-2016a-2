package ar.edu.itba.models;

import java.util.Set;
import java.util.TreeSet;

public class Iteration {
	
	private IterationDetail details;
	private Set<Task> tasks;
	private Set<Log> logs;

	public Iteration(IterationDetail details) {
		this.details = details;
		this.tasks = new TreeSet<Task>();
		this.logs = new TreeSet<Log>();
	}
	
	public IterationDetail getDetails() {
		return details;
	}
	
	public Set<Task> getTasks() {
		return tasks;
	}
	
	public Set<Log> getLogs() {
		return logs;
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void addLog(Log log) {
		logs.add(log);
	}

}
