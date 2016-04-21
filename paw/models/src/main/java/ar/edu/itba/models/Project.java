package ar.edu.itba.models;

import java.util.Date;

public class Project {
	
	private final int projectId;
	private String name;
	private String description;
	private Date startDate;
	private ProjectStatus status;
	
	public Project (int projectId, String name, String description, Date startDate, ProjectStatus status) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.status = status;
	}
	
	public Project (int projectId, String name, String description) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.status = ProjectStatus.OPEN;
		this.startDate = new Date();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public int getProjectId() {
		return projectId;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", description=" + description
				+ ", startDate=" + startDate + ", status=" + status + "]";
	}
	
}
