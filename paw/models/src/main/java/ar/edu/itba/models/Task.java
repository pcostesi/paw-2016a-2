package ar.edu.itba.models;

public class Task{
	
	private final int taskId;
	private String title;
	private String description;
	private TaskStatus status;
	private TaskScore score;
	private TaskPriority priority;
	private User owner;
	private Story story;
	
	public Task(int taskId, String title, String description, TaskStatus status, TaskScore score, TaskPriority priority, User owner) {
		this.taskId = taskId;
		this.title = title;
		this.description = description;
		this.score = score;
		this.priority = priority;
		this.status = status;
		this.owner = owner;
	}
	
	public Task(int taskId, String title, String description) {
		this.taskId = taskId;
		this.title = title;
		this.description = description;
		this.status = TaskStatus.NOT_STARTED;
		this.score = TaskScore.NORMAL;
		this.priority = TaskPriority.NORMAL;
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

	public int getTaskId() {
		return taskId;
	}

	public Story getStory() {
		return story;
	}
	
	public TaskPriority getPriority() {
		return priority;
	}
	
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	
	public TaskScore getScore() {
		return score;
	}
	
	public String toString() {
		return "Task [taskId=" + taskId + ", title=" + title + ", status=" + status.getLabel() 
			+ ", score=" + score.getLabel() + ", priority=" + priority.getLabel() + ", owner=" + owner + "]";
	}

	public void setScore(TaskScore score) {
		this.score = score;
	}

	public void setStory(Story story) {
		this.story = story;
	}

}
