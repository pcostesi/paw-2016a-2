package ar.edu.itba.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Project {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public abstract int projectId();
	public abstract String name();
	public abstract String code();
	public abstract String description();
	public abstract LocalDate startDate();
	
	@Value.Lazy
	public String formattedStartDate() {
		return startDate().format(formatter);
	}

}
