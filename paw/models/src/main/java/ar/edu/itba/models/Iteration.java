package ar.edu.itba.models;

import java.time.LocalDate;
import java.util.List;

public interface Iteration {

	public int iterationId();

	public int number();

	public LocalDate startDate();

	public LocalDate endDate();

	public int projectId();

	public List<Story>getStories();
}
