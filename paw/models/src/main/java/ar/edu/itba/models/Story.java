package ar.edu.itba.models;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Story {

	public abstract int storyId();
	public abstract String title();
	public abstract Iteration iteration();

}
