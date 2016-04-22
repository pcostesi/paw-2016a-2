package ar.edu.itba.models;

public class Story {

	private final int storyId;
	private String title;

	public Story(final int storyId, final String title) {
		this.storyId = storyId;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStoryId() {
		return storyId;
	}
	
}
