package ar.edu.itba.webapp.form;

import javax.validation.constraints.Size;

import ar.edu.itba.webapp.form.constraint.StoryNameFree;

@StoryNameFree(markedField="title")
public class StoryForm {
	
	@Size(min=0, max=100)
	private String title;
	
	private int iterationId;
	
	private String oldTitle;

	public int getIterationId() {
		return iterationId;
	}

	public void setIterationId(int iterationId) {
		this.iterationId = iterationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOldTitle() {
		return oldTitle;
	}

	public void setOldTitle(String oldTitle) {
		this.oldTitle = oldTitle;
	}

}
