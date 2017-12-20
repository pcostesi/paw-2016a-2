package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.interfaces.service.EventService;
import ar.edu.itba.interfaces.service.ExperienceService;
import ar.edu.itba.interfaces.service.TaskService;
import ar.edu.itba.models.*;
import ar.edu.itba.models.event.LogEvent;
import ar.edu.itba.models.event.TaskCreatedEventBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskDao taskDao;

    @Autowired
    private final StoryDao storyDao;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EventService eventService;

    @Autowired
    TaskServiceImpl(final TaskDao newTaskDao, final StoryDao newStoryDao) {
        this.taskDao = newTaskDao;
        this.storyDao = newStoryDao;
    }

    @Override
    public Task createTask(final Story story, final String title, final String description, final Status status, final User user, final Score score, final Priority priority) {
        if (story == null) {
            throw new IllegalArgumentException("Story can't be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Task title can't be null");
        }

        if (status == null) {
            throw new IllegalArgumentException("Task status can't be null");
        }

        if (score == null) {
            throw new IllegalArgumentException("Task score can't be null");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("Task title can't be empty");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Task title can't be longer than 100 characters");
        }

        if (user != null && user.username().length() == 0) {
            throw new IllegalArgumentException("Task owner can't be empty");
        }

        if (user != null && user.username().length() > 100) {
            throw new IllegalArgumentException("Task owner can't be longer than 100 characters");
        }

        if (description != null && description.length() == 0) {
            throw new IllegalArgumentException("Description can't be empty");
        }

        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("Description can't be longer than 500 characters");
        }
        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story doesn't exist");
        }

        if (taskDao.taskExists(story, title)) {
            throw new IllegalStateException("Task with name " + title + " already exists in this story");
        }

        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't add tasks to a finished iteration");
        }

        Task task = taskDao.createTask(story, title, description, status, user, score, priority);
        LogEvent event = new TaskCreatedEventBuilder()
            .actor(Optional.empty())
            .time(LocalDateTime.now())
            .project(story.iteration().project())
            .task(task)
            .build();
        eventService.emit(event);
        return task;
    }

    @Override
    public Task getTaskById(final int taskId) {
        if (taskId < 0) {
            throw new IllegalArgumentException("Invalid task id");
        }

        final Task task = taskDao.getTaskById(taskId);

        if (task == null) {
            throw new IllegalStateException("Task doesn't exist");
        } else {
            return task;
        }
    }

    @Override
    public void deleteTask(final Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (task.status() == Status.COMPLETED) {
            experienceService.removeExperience(task);
        }

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't delete a task from a finished iteration");
        }

        taskDao.deleteTask(task);
    }

    @Override
    public Task changeOwnership(final Task task, final User user) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (task.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't change ownership of a completed task");
        }

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit a task from a finished iteration");
        }

        taskDao.updateOwner(task, user);

        return taskDao.getTaskById(task.taskId());
    }

    @Override
    public Task changeStatus(final Task task, final Status status) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        if (task.status().equals(status)) {
            return task;
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit a task from a finished iteration");
        }

        final Status oldStatus = task.status();

        taskDao.updateStatus(task, status);

        if (oldStatus != Status.COMPLETED && status == Status.COMPLETED) {
            experienceService.addExperience(task);
        }
        if (oldStatus == Status.COMPLETED && status != Status.COMPLETED) {
            experienceService.removeExperience(task);
        }

        return taskDao.getTaskById(task.taskId());
    }

    @Override
    public List<Task> getTasksForStory(final Story story) {
        if (story == null) {
            throw new IllegalArgumentException("Story can't be null");
        }

        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story doesn't exist");
        }

        return taskDao.getTasksForStory(story);
    }

    @Override
    public Story getParent(final Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        return taskDao.getParent(task);
    }

    @Override
    public Task changePriority(final Task task, final Priority priority) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (priority == null) {
            throw new IllegalArgumentException("Priority can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        if (task.priority().equals(priority)) {
            return task;
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit a task from a finished iteration");
        }

        taskDao.updatePriority(task, priority);
        return taskDao.getTaskById(task.taskId());
    }

    @Override
    public Task changeScore(final Task task, final Score score) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (score == null) {
            throw new IllegalArgumentException("Score can't be null");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        if (task.score().equals(score)) {
            return task;
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit a task from a finished iteration");
        }

        experienceService.removeExperience(task);
        taskDao.updateScore(task, score);

        final Task updatedTask = Task.builder().from(task).score(score).build();
        experienceService.addExperience(updatedTask);

        return taskDao.getTaskById(task.taskId());
    }

    @Override
    public Task changeTitle(final Task task, final String title) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Task title can't be null");
        }

        if (title.length() == 0) {
            throw new IllegalArgumentException("Task title can't be empty");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Task title can't be longer than 100 characters");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        if (task.title().equals(title)) {
            return task;
        }

        if (taskDao.taskExists(taskDao.getParent(task), title)) {
            throw new IllegalStateException("Task with name " + title + " already exists in this story");
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit a task from a finished iteration");
        }

        taskDao.updateTitle(task, title);

        return taskDao.getTaskById(task.taskId());
    }

    @Override
    public Task changeDescription(final Task task, final String description) {
        if (task == null) {
            throw new IllegalArgumentException("Task can't be null");
        }

        if (description != null && description.length() == 0) {
            throw new IllegalArgumentException("Description can't be empty");
        }

        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("Description can't be longer than 500 characters");
        }

        if (!taskDao.taskExists(task)) {
            throw new IllegalStateException("Task doesn't exist");
        }

        if (task.description().isPresent() && task.description().get().equals(description)) {
            return task;
        }

        final Story story = taskDao.getParent(task);
        final Iteration iteration = storyDao.getParent(story);

        if (iteration.status() == Status.COMPLETED) {
            throw new IllegalStateException("Can't edit a task from a finished iteration");
        }

        taskDao.updateDescription(task, description);

        return taskDao.getTaskById(task.taskId());
    }

    @Override
    public boolean taskNameExists(final Story story, final String title) {
        if (story == null) {
            throw new IllegalArgumentException("Story can't be null");
        }

        if (!storyDao.storyExists(story)) {
            throw new IllegalStateException("Story doesn't exist");
        }

        if (title == null) {
            throw new IllegalArgumentException("Title can't be null");
        }

        return taskDao.taskExists(story, title);
    }

}
