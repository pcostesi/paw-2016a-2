package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.BacklogDao;
import ar.edu.itba.interfaces.dao.ProjectDao;
import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.interfaces.service.BacklogService;
import ar.edu.itba.interfaces.service.EventService;
import ar.edu.itba.models.BacklogItem;
import ar.edu.itba.models.Project;
import ar.edu.itba.models.Story;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.event.BacklogItemCreatedEvent;
import ar.edu.itba.models.event.BacklogItemCreatedEventBuilder;
import ar.edu.itba.models.event.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BacklogServiceImpl implements BacklogService {

    @Autowired
    private
    BacklogDao backlogDao;

    @Autowired
    private
    ProjectDao projectDao;

    @Autowired
    private
    TaskDao taskDao;

    @Autowired
    private
    StoryDao storyDao;

    @Autowired
    private
    EventService eventService;

    @Override
    public BacklogItem createBacklogItem(final Project project, final String title, final String description) {
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

        if (!projectDao.projectExists(project)) {
            throw new IllegalStateException("Project does not exist");
        }

        if (backlogDao.backlogItemExists(project, title)) {
            throw new IllegalStateException("There is another backlog item with this title in the project");
        }

        BacklogItem item = backlogDao.createBacklogItem(title, description, project);
        LogEvent event = new BacklogItemCreatedEventBuilder()
            .actor(Optional.empty())
            .item(item)
            .project(item.project())
            .time(LocalDateTime.now())
            .build();
        eventService.emit(event);
        return item;
    }

    @Override
    public void deleteBacklogItem(final BacklogItem backlogItem) {
        if (backlogItem == null) {
            throw new IllegalArgumentException("Item can't be null");
        }

        if (!backlogDao.backlogItemExists(backlogItem)) {
            throw new IllegalStateException("Backlog item doesn't exist");
        }

        backlogDao.deleteItem(backlogItem);
    }

    @Override
    public List<BacklogItem> getBacklogForProject(final Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project can't be null");
        }

        if (!projectDao.projectExists(project)) {
            throw new IllegalStateException("Project does not exist");
        }

        return backlogDao.getBacklogForProject(project);
    }

    @Override
    public BacklogItem setBacklogItemTitle(final BacklogItem item, final String title) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Name can't be null");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("Backlog item title can't be empty");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Backlog item title can't be more than 100 characters long");
        }

        if (!backlogDao.backlogItemExists(item)) {
            throw new IllegalStateException("Backlog item does not exist");
        }

        if (backlogDao.backlogItemExists(backlogDao.getParent(item), title)) {
            throw new IllegalStateException("There is another item with this title in this project");
        }

        backlogDao.updateTitle(item, title);

        return backlogDao.getBacklogItemById(item.backlogItemId());
    }

    @Override
    public BacklogItem setBacklogItemDescription(final BacklogItem item, final String description) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null");
        }

        if (!backlogDao.backlogItemExists(item)) {
            throw new IllegalStateException("Item does not exist in this project");
        }

        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("Description can't be more than 500 characters long");
        }

        backlogDao.updateDescription(item, description);

        return backlogDao.getBacklogItemById(item.backlogItemId());
    }


    @Override
    public BacklogItem createBacklogItemFromTask(final Task task, final Project project) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        if (project == null) {
            throw new IllegalArgumentException("Project can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task does not exist");
        }

        if (!projectDao.projectExists(project)) {
            throw new IllegalStateException("Project does not exist");
        }

        //noinspection ConstantConditions
        final BacklogItem newItem = createBacklogItem(project, task.title(), task.description().get());
        taskDao.deleteTask(task);

        return newItem;
    }

    @Override
    public BacklogItem createBacklogItemFromStory(final Story story, final Project project) {
        if (story == null) {
            throw new IllegalArgumentException("Story cannot be null");
        }

        if (project == null) {
            throw new IllegalArgumentException("Project can't be null");
        }

        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story does not exist");
        }

        if (!projectDao.projectExists(project)) {
            throw new IllegalStateException("Project does not exist");
        }

        final BacklogItem newItem = createBacklogItem(project, story.title(), null);
        storyDao.deleteStory(story);

        return newItem;
    }

    @Override
    public BacklogItem getBacklogById(final int itemId) {
        if (itemId < 0) {
            throw new IllegalArgumentException("Invalid backlog id");
        }

        final BacklogItem item = backlogDao.getBacklogItemById(itemId);

        if (item == null) {
            throw new IllegalStateException("Backlog item doesn't exist");
        } else {
            return item;
        }
    }

    @Override
    public boolean titleIsUsed(final Project project, final String title) {
        if (project == null) {
            throw new IllegalArgumentException("Project can't be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Backlog item title can't be null");
        }

        return backlogDao.backlogItemExists(project, title);
    }

}
