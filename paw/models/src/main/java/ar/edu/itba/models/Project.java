package ar.edu.itba.models;

import java.util.Date;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Project {
	
	public abstract int projectId();
	public abstract String name();
	public abstract String code();
	public abstract String description();
	public abstract Date startDate();

}
