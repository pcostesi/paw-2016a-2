package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

public interface BacklogService {

	public BacklogItem createBacklogItem(final Project project, String name, String description);
	
	public void deleteBacklogItem(final BacklogItem backlogItem);
	
	public List<BacklogItem> getBacklogForProject(final Project project);
		
	public BacklogItem setBacklogItemName(final BacklogItem item, final String name);
	
	public Iteration setBacklogItemDescription(final BacklogItem item, final String description);

	public BacklogItem createBacklogItemFromTask(final Task task);

	public BacklogItem createBacklogItemFromStory(final Story story);
	
}
