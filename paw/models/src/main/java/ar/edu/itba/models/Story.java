package ar.edu.itba.models;

public class Story {

	private final int storyId;
	private String title;
	private Iteration iteration;

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

	public Iteration getIteration() {
		return iteration;
	}

	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}
	
}
