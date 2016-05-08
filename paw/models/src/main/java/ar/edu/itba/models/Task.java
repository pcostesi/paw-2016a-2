package ar.edu.itba.models;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Task{
	
	public abstract int taskId();
	public abstract String title();
	public abstract Optional<String> description();
	public abstract TaskStatus status();
	public abstract TaskScore score();
	public abstract TaskPriority priority();
	public abstract Optional<User> owner();
	public abstract int story();
	
}
