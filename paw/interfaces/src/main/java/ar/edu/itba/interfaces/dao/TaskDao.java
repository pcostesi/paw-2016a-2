package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.*;

import java.util.List;

public interface TaskDao {

    List<Task> getTasksForStory(final Story story);

    boolean taskExists(final Task task);

    boolean taskExists(final Story story, final String title);

    void updateStatus(final Task task, final Status status);

    void updateOwner(final Task task, final User user);

    void deleteTask(final Task task);

    Task getTaskById(final int taskId);

    Task createTask(final Story story, final String name, final String description, final Status status, final User user, final Score score, final Priority priority);

    void updatePriority(final Task task, final Priority priority);

    void updateScore(final Task task, final Score score);

    Story getParent(final Task task);

    void updateTitle(final Task task, final String title);

    void updateDescription(final Task task, final String description);

    List<Task> getUnfinishedTasks(final Story oldStory);

    void cloneTaskToStory(final Task task, final Story story);

}
