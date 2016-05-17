package ar.edu.itba.models;

import java.time.LocalDate;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Iteration{
		
	public abstract int iterationId();
	public abstract int number();
	public abstract LocalDate startDate();
	public abstract LocalDate endDate();

}
