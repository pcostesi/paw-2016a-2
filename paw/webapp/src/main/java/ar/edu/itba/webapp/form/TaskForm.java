package ar.edu.itba.webapp.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.models.User;

public class TaskForm {
	
	@NotEmpty
	private String title;
	
	@Length(max = 500)
	private String description;
	
	@NotNull
	private TaskStatus status;
	
	@NotNull
	private TaskScore score;
	
	private User owner;
	
	public TaskScore getScore() {
		return score;
	}

	public void setScore(TaskScore score) {
		this.score = score;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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
}
