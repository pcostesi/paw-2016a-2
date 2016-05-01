package ar.edu.itba.models;

import java.util.Date;

public class Project {
	
	private final int projectId;
	private String name;
	private String code;
	private String description;
	private Date startDate;
	
	public Project (int projectId, String name, String codeName, String description, Date startDate) {
		this.projectId = projectId;
		this.code = codeName;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
	}
	
	public Project (int projectId, String name, String codeName, String description) {
		this.projectId = projectId;
		this.name = name;
		this.description = description;
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

	public int getProjectId() {
		return projectId;
	}
	
	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", description=" + description
				+ ", startDate=" + startDate +"]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;		
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
