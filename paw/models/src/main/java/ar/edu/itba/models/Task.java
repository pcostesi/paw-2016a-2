package ar.edu.itba.models;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Task{
	
	public abstract int taskId();
	public abstract String title();
	public abstract Optional<String> description();
	public abstract Status status();
	public abstract Score score();
	public abstract Priority priority();
	public abstract Optional<String> owner();
	
}
