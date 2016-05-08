package ar.edu.itba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.interfaces.BacklogService;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.ImmutableBacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

@Service
public class BacklogServiceImpl implements BacklogService {

	@Autowired
	BacklogDao backlogDao;

	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	StoryDao storyDao;

	@Override
	public BacklogItem createBacklogItem(Project project, String title, String description) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if (title == null) {
			throw new IllegalArgumentException("Backlog item title can't be null");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Backlog item title can't be more than 100 characters long");
		}
		
		if (description != null && description.length() > 500) {
			throw new IllegalArgumentException("Backlog item description can't be more than 500 characters long");
		}
		
		if (!projectDao.projectExists(project.projectId())) {
			throw new IllegalStateException("Project does not exist");
		}
		
		if (backlogDao.backlogItemExists(title, project.projectId())) {
			throw new IllegalStateException("There is another backlog item with this title in the project");
		}		

		return backlogDao.createBacklogItem(title, description, project.projectId());
	}

	@Override
	public void deleteBacklogItem(BacklogItem backlogItem) {
		if (backlogItem == null) {
			throw new IllegalArgumentException("Item can't be null");
		}
		
		if (!backlogDao.backlogItemExists(backlogItem.backlogItemId())) {
			throw new IllegalStateException("Backlog item doesn't exist");
		}

		backlogDao.deleteItem(backlogItem.backlogItemId());
	}

	@Override
	public List<BacklogItem> getBacklogForProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectExists(project.projectId())) {
			throw new IllegalStateException("Project does not exist");
		}

		return backlogDao.getBacklogForProject(project.projectId());
	}

	@Override
	public BacklogItem setBacklogItemTitle(BacklogItem item, String title) {
		if (item == null) {
			throw new IllegalArgumentException("Item can't be null");
		}
		
		if (title == null ) {
			throw new IllegalArgumentException("Name can't be null");
		}
		
		if (title.length() == 0) {
			throw new IllegalArgumentException("Backlog item title can't be empty");
		}
		
		if (title.length() > 100) {
			throw new IllegalArgumentException("Backlog item title can't be more than 100 characters long");
		}
								
		if (!backlogDao.backlogItemExists(item.backlogItemId())) {
			throw new IllegalStateException("Backlog item does not exist");
		}		
		
		final int parentId = backlogDao.getParent(item.backlogItemId());
		
		if (backlogDao.backlogItemExists(title, parentId)) {
			throw new IllegalStateException("There is another item with this title in this project");
		}		
		
		backlogDao.updateTitle(item.backlogItemId(), title);
		return ImmutableBacklogItem.copyOf(item).withTitle(title);
	}

	@Override
	public BacklogItem setBacklogItemDescription(BacklogItem item, String description) {
		if (item == null) {
			throw new IllegalArgumentException("Item can't be null");
		}
		
		if (!backlogDao.backlogItemExists(item.backlogItemId())) {
			throw new IllegalStateException("Item does not exist in this project");
		}
		
		if (description != null && description.length() > 500) {
			throw new IllegalArgumentException("Description can't be more than 500 characters long");
		}
		
		backlogDao.updateDescription(item.backlogItemId(), description);
		return ImmutableBacklogItem.copyOf(item).withDescription(Optional.ofNullable(description));
	}
	

	@Override
	public BacklogItem createBacklogItemFromTask(Task task, Project project) {
		if(task == null){
			throw new IllegalArgumentException("Task cannot be null");
		}
		
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if(!taskDao.taskExists(task.taskId())){
			throw new IllegalStateException("Task does not exist");
		}
		
		if (!projectDao.projectExists(project.projectId())) {
			throw new IllegalStateException("Project does not exist");
		}
		
		final BacklogItem newItem = createBacklogItem(project, task.title(), task.description().isPresent()? task.description().get() : null);
		taskDao.deleteTask(task.taskId());
		
		return newItem;
	}

	@Override
	public BacklogItem createBacklogItemFromStory(Story story, Project project) {
		if(story == null){
			throw new IllegalArgumentException("Story cannot be null");
		}
		
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		
		if(!storyDao.storyExists(story.storyId())){
			throw new IllegalStateException("Story does not exist");
		}
		
		if (!projectDao.projectExists(project.projectId())) {
			throw new IllegalStateException("Project does not exist");
		}
		
		final BacklogItem newItem = createBacklogItem(project, story.title(), null); 
		storyDao.deleteStory(story.storyId());
		
		return newItem;
	}

}
