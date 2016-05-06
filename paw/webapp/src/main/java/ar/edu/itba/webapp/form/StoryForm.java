package ar.edu.itba.webapp.form;

import javax.validation.constraints.NotNull;

public class StoryForm {
	
	@NotNull
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
