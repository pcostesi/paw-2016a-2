package ar.edu.itba.models;

import org.immutables.value.Value;

@Value.Immutable
public abstract class User {
	
	public abstract String username();
	public abstract String password();
	public abstract String mail();
	
}
