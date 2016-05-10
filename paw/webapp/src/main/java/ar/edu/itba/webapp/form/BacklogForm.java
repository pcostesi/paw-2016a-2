package ar.edu.itba.webapp.form;

import javax.validation.constraints.Size;

import ar.edu.itba.webapp.form.constraint.ItemTitleFree;

@ItemTitleFree(markedField="title")
public class BacklogForm {

	@Size(min=1, max=100)
	private String title;
	
	@Size(max=500)
	private String description;
	
	private int projectId;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
	
}
