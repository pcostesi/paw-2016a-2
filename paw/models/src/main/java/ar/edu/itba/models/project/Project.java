package ar.edu.itba.models.project;

import java.util.Set;
import java.util.TreeSet;

import ar.edu.itba.models.iteration.IterationDetail;

public class Project {

	private ProjectDetail details;
	private Set<IterationDetail> iterations;
	
	public Project(ProjectDetail details) {
		this.details = details;
		this.iterations = new TreeSet<IterationDetail>();
	}

	public ProjectDetail getProjectDetails() {
		return details;
	}
	
	public Set<IterationDetail> getIterationsDetails() {
		return iterations;
	}
	
	public void addIteration(IterationDetail iterationDetail) {
		iterations.add(iterationDetail);
	}

	public String toString() {
		return details.toString();
	}
	
}
