package ar.edu.itba.models;

import org.immutables.value.Value;

@Value.Immutable
public abstract class BacklogItem {
	
	public abstract String name();
	public abstract String description();
	public abstract int backlogItemID();
	
}
