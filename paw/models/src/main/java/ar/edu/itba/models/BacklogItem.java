package ar.edu.itba.models;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class BacklogItem {
	
	public abstract int backlogItemId();
	public abstract String title();
	public abstract Optional<String> description();
	
}
