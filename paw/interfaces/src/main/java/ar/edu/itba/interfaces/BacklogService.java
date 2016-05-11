package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

public interface BacklogService {

	public BacklogItem createBacklogItem(final Project project, final String title, final String description);
	
	public void deleteBacklogItem(final BacklogItem backlogItem);
	
	public List<BacklogItem> getBacklogForProject(final Project project);
		
	public BacklogItem setBacklogItemTitle(final BacklogItem item, final String title);
	
	public BacklogItem setBacklogItemDescription(final BacklogItem item, final String description);

	public BacklogItem createBacklogItemFromTask(final Task task, final Project project);

	public BacklogItem createBacklogItemFromStory(final Story story, final Project project);

	public BacklogItem getBacklogById(final int backlogId);

	public boolean titleIsUsed(final Project project, final String title);
	
}
