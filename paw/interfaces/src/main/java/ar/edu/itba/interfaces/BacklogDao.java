package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.BacklogItem;

public interface BacklogDao {

	BacklogItem createBacklogItem(String name, String description, int projectId);

	boolean backlogItemExists(String name, String description, int projectId);

	boolean backlogItemExists(int itemId);
	
	void deleteItem(int itemId);
	
	List<BacklogItem> getBacklogForProject(int projectId);
}
