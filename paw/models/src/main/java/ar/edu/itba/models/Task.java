package ar.edu.itba.models;

public class Task {
	
	private int taskId;
	
	private String title;
	private String description;
	private TaskStatus status;
	private User owner;
	
	public Task() {
	}
	
	public Task(int taskId, String title, String description, TaskStatus status, User owner) {
		this.taskId = taskId;
		this.title = title;
		this.description = description;
		this.status = status;
		this.owner = owner;
	}
	
	public Task(int taskId, String title, String description) {
		this.taskId = taskId;
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

	public String toString() {
		return "Task [taskId=" + taskId + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", owner=" + owner + "]";
	}

	public int getTaskId() {
		return taskId;
	}
	

}
