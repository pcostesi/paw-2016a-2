package ar.edu.itba.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Iteration{
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public abstract int iterationId();
	public abstract int number();
	public abstract LocalDate startDate();
	public abstract LocalDate endDate();
	public abstract int project();
	
	@Value.Lazy
	public String formattedStartDate() {
		return startDate().format(formatter);
	}
	
	@Value.Lazy
	public String formattedEndDate() {
		return endDate().format(formatter);
	}

}
