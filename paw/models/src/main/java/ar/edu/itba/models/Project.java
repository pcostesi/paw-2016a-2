package ar.edu.itba.models;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Project {

	private String name;
	private String description;
	private Date startDate;
	private ProjectStatus status;
	private Set<Iteration> iterations;
	
	public Project (String name, String description, Date startDate, ProjectStatus status) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.iterations = new TreeSet<Iteration>();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<Iteration> getIterations() {
		return iterations;
	}
	
	public void createIteration(Date startDate, Date endDate) {
		iterations.add(new Iteration(startDate, endDate));
	}

	public Date getStartDate() {
		return startDate;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
	
}
