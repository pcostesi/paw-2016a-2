package ar.edu.itba.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BacklogDao;
import ar.edu.itba.interfaces.BacklogService;
import ar.edu.itba.interfaces.ProjectDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;

@Service
public class BacklogServiceIml implements BacklogService {

	@Autowired
	BacklogDao backlogDao;

	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	StoryDao storyDao;

	@Override
	public BacklogItem createBacklogItem(Project project, String name, String description) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		if (name == null || description == null) {
			throw new IllegalArgumentException("Name nor description can be null");
		}
		if (name.length() > 100 || description.length() > 100) {
			throw new IllegalArgumentException("Name nor description can be more than 100 characters long");
		}
		if (backlogDao.backlogItemExists(name, description, project.getProjectId())) {
			throw new IllegalStateException("Backlog Item already exist");
		}
		if (!projectDao.projectCodeExists(project.getCode())) {
			throw new IllegalStateException("Project does not exist");
		}

		return backlogDao.createBacklogItem(name, description, project.getProjectId());
	}

	@Override
	public void deleteBacklogItem(BacklogItem backlogItem) {
		if (backlogItem == null) {
			throw new IllegalArgumentException("Item can't be null");
		}
		if (!backlogDao.backlogItemExists(backlogItem.getBacklogItemID())) {
			return;
		}

		backlogDao.deleteItem(backlogItem.getBacklogItemID());
	}

	@Override
	public List<BacklogItem> getBacklogForProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}

		if (!projectDao.projectCodeExists(project.getCode())) {
			throw new IllegalStateException("Project does not exist");
		}

		return backlogDao.getBacklogForProject(project.getProjectId());
	}

	@Override
	public BacklogItem setBacklogItemName(BacklogItem item, String name, Project project) {
		if (item == null) {
			throw new IllegalArgumentException("Item can't be null");
		}
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		if (!projectDao.projectCodeExists(project.getCode())) {
			throw new IllegalStateException("Project does not exist");
		}
		if (!backlogDao.backlogItemExists(item.getBacklogItemID())) {
			throw new IllegalStateException("Item does not exist in this project");
		}
		if (backlogDao.backlogItemExists(name, item.getDescription(), project.getProjectId())) {
			throw new IllegalStateException("Item already exists in this project");
		}
		if (name == null ) {
			throw new IllegalArgumentException("Name can't be null");
		}
		if (name.length() > 100) {
			throw new IllegalArgumentException("Name can't be more than 100 characters long");
		}
		return backlogDao.createBacklogItem(name, item.getDescription(), project.getProjectId());
	}

	@Override
	public BacklogItem setBacklogItemDescription(BacklogItem item, String description, Project project) {
		if (item == null) {
			throw new IllegalArgumentException("Item can't be null");
		}
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		if (!projectDao.projectCodeExists(project.getCode())) {
			throw new IllegalStateException("Project does not exist");
		}
		if (!backlogDao.backlogItemExists(item.getBacklogItemID())) {
			throw new IllegalStateException("Item does not exist in this project");
		}
		if (backlogDao.backlogItemExists(item.getName(), description, project.getProjectId())) {
			throw new IllegalStateException("Item already exists in this project");
		}
		if (description == null ) {
			throw new IllegalArgumentException("Description can't be null");
		}
		if (description.length() > 100) {
			throw new IllegalArgumentException("Description can't be more than 100 characters long");
		}
		return backlogDao.createBacklogItem(item.getName(), description, project.getProjectId());
	}
	

	@Override
	public BacklogItem createBacklogItemFromTask(Task task, Project project) {
		if(task == null){
			throw new IllegalArgumentException("Task cannot be null");
		}
		if(!taskDao.taskExists(task.getTaskId())){
			throw new IllegalStateException("Task does not exist");
		}
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		if (!projectDao.projectCodeExists(project.getCode())) {
			throw new IllegalStateException("Project does not exist");
		}
		return createBacklogItem(project, task.getTitle(), task.getDescription());
	}

	@Override
	public BacklogItem createBacklogItemFromStory(Story story, Project project) {
		if(story == null){
			throw new IllegalArgumentException("Story cannot be null");
		}
		if(!storyDao.storyExists(story.getStoryId())){
			throw new IllegalStateException("Story does not exist");
		}
		if (project == null) {
			throw new IllegalArgumentException("Project can't be null");
		}
		if (!projectDao.projectCodeExists(project.getCode())) {
			throw new IllegalStateException("Project does not exist");
		}
		return null;
	}

}
