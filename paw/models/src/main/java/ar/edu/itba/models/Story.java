package ar.edu.itba.models;

public class Story {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoryId() {
		return storyId;
	}

	private final int storyId;
	private String name;

	public Story(final int storyId, final String name) {
		this.storyId = storyId;
		this.name = name;
	}
	
}
