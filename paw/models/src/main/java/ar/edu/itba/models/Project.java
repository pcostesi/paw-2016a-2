package ar.edu.itba.models;

import java.time.LocalDate;
import java.util.List;

public interface Project {

	public int projectId();

	public String name();

	public String code();

	public String description();

	public LocalDate startDate();
	
	public List<Iteration> geIterations();
	
	public List<BacklogItem> getBacklogItems();
}
