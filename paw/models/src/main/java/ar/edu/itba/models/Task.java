package ar.edu.itba.models;

public class Task {
	
	private String title;
	private String description;
	private TaskStatus status;
	private User owner;
	
	public Task(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = TaskStatus.NOT_STARTED;
		this.owner = null;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TaskStatus getStatus() {
		return status;
	}
	
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	

}
