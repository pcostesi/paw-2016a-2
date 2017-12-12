package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Project;
import ar.edu.itba.models.Task;
import ar.edu.itba.models.User;

import java.util.Map;

public interface ExperienceService {

    /**
     * Add experience to the owner of a completed task
     *
     * @param task Task completed.
     * @return true if the experience was added, false otherwise.
     */
    boolean addExperience(final Task task);

    /**
     * Remove experience to the owner of a task for opening a completed task
     *
     * @param task Task opened.
     * @return true if the experience was removed, false otherwise.
     */
    boolean removeExperience(final Task task);

    /**
     * Get the total experience of a user
     *
     * @param user User to get the experience from.
     * @return The number of experience a user has.
     */
    int getTotalExperience(final User user);

    /**
     * Get the experience of the members of a project
     *
     * @param project Project to get the experience from.
     * @return Map of the project members and their experience.
     */
    Map<User, Integer> getProjectExperience(final Project project);

}