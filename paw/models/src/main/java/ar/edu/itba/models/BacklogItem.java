package ar.edu.itba.models;

import java.util.Optional;

public interface BacklogItem {

	public int backlogItemId();

	public String title();

	public Optional<String> description();
	
	public int projectId();
}
