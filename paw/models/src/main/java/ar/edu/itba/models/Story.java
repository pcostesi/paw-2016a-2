package ar.edu.itba.models;

import java.util.List;

public interface Story {

	public int storyId();

	public String title();

	public Status status();

	public int totalScore();

	public int iterationId();

	public List<Task> tasks();
}
