package ar.edu.itba.models;

import java.util.Optional;

public interface Task {

	public int taskId();

	public String title();

	public Optional<String> description();

	public Status status();

	public Score score();

	public Priority priority();

	public Optional<String> owner();
	
	public int storyId();
}
