package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.BacklogItem;

public interface BacklogDao {

	public BacklogItem createBacklogItem(final String title, final String description, final int projectId);

	public boolean backlogItemExists(final String title, int projectId);

	public boolean backlogItemExists(final int itemId);
	
	public void deleteItem(final int itemId);
	
	public void updateTitle(final int itemId, final String title);
	
	public void updateDescription(final int itemId, final String description);
	
	public List<BacklogItem> getBacklogForProject(final int projectId);

	public int getParent(final int backlogItemId);
}
