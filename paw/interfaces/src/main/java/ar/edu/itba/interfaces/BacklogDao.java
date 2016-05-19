package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;

public interface BacklogDao {

	public BacklogItem createBacklogItem(final String title, final String description, final Project project);

	public boolean backlogItemExists(final Project project, final String title);

	public boolean backlogItemExists(final BacklogItem backlogItem);
	
	public void deleteItem(final BacklogItem backlogItem);
	
	public void updateTitle(final BacklogItem backlogItem, final String title);
	
	public void updateDescription(final BacklogItem backlogItem, final String description);
	
	public List<BacklogItem> getBacklogForProject(final Project project);

	public Project getParent(final BacklogItem backlogItem);

	public BacklogItem getBacklogItemById(final int backlogItemId);

}
