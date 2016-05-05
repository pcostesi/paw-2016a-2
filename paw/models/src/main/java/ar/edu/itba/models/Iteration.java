package ar.edu.itba.models;

import java.util.Date;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Iteration{
	
	public abstract int iterationId();
	public abstract int number();
	public abstract Date startDate();
	public abstract Date endDate();
	public abstract Project project();

}
