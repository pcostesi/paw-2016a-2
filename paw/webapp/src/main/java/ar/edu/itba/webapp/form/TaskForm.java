package ar.edu.itba.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ar.edu.itba.models.TaskScore;
import ar.edu.itba.models.TaskStatus;
import ar.edu.itba.webapp.form.constraint.TaskNameFree;

@TaskNameFree(title="title")
public class TaskForm {
	
	@Size(min=1, max=100)
	private String title;
	
	@Size(min=1, max=500)
	private String description;
	
	@NotNull
	private TaskStatus status;
	
	@NotNull
	private TaskScore score;
	
	private String owner;
	
	private String oldTitle;
	
	private int storyId;	
	
	public int getStoryId() {
		return storyId;
	}

	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}

	public TaskScore getScore() {
		return score;
	}

	public void setScore(TaskScore score) {
		this.score = score;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
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

	public String getOldTitle() {
		return oldTitle;
	}

	public void setOldTitle(String oldTitle) {
		this.oldTitle = oldTitle;
	}

}
