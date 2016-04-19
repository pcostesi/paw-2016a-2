package ar.edu.itba.webapp.form;

import ar.edu.itba.models.TaskStatus;

public class TaskForm {
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

	private String title;
	private String description;
	private TaskStatus status;
}
