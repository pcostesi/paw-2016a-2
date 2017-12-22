package ar.edu.itba.interfaces.service;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

import java.time.LocalDate;
import java.util.List;

public interface IterationService {

    /**
     * Create a new iteration instance and persist it.
     *
     * @param project   Project where the iteration will be created.
     * @param startDate Date when the iteration starts.
     * @param endDate   Date when the iteration ends.
     * @return The persisted instance.
     */
    Iteration createIteration(final Project project, final LocalDate startDate, final LocalDate endDate);

    /**
     * Create a new iteration inheriting unfinished tasks/stories from another instance and persist it.
     *
     * @param project                Project where the iteration will be created.
     * @param startDate              Date when the iteration starts.
     * @param endDate                Date when the iteration ends.
     * @param inheritIterationNumber Number of the iteration to inherit from
     * @return The persisted instance.
     */
    Iteration createIteration(final Project project, final LocalDate startDate, final LocalDate endDate, final int inheritIterationNumber);

    /**
     * Remove iteration from database.
     *
     * @param iteration Iteration instance to delete.
     */
    void deleteIteration(final Iteration iteration);

    /**
     * Get iteration from a project by its iteration number.
     *
     * @param project         Project to get the iteration from.
     * @param iterationNumber The number of the iteration.
     * @return The iteration instance
     */
    Iteration getIteration(final Project project, int iterationNumber);

    /**
     * Get an interation instance by its id.
     *
     * @param iterationId The id of the iteration.
     * @return The iteration instance.
     */
    Iteration getIterationById(int iterationId);

    /**
     * Get all the iterations of a project.
     *
     * @param project Project to get iterations for.
     * @return List of the iteration instances.
     */
    List<Iteration> getIterationsForProject(final Project project);

    /**
     * Get parent project.
     *
     * @param iteration Iteration to get parent from.
     * @return Project instance.
     */
    Project getParent(final Iteration iteration);

    /**
     * Checks if it is a valid date range for the project or collides with another iteration.
     *
     * @param project   Project to check at.
     * @param iteration Iteration to exclude from collision check. Can be null.
     * @param startDate Start date to check from.
     * @param endDate   End date to check to.
     * @return True if there isn't any iteration collision, false otherwise.
     */
    boolean isValidDateRangeInProject(final Project project, final Iteration iteration, final LocalDate startDate, final LocalDate endDate);

    /**
     * Gets the number of the last completed iteration (no longer active).
     *
     * @param project Project to get it from.
     * @return The number of iteration. 0 if there isn't any.
     */
    Integer getLastFinishedIterationNumber(final Project project);

    /**
     * Set dates to an iteration.
     *
     * @param iteration Iteration to set dates to.
     * @param startDate New begin date for the iteration.
     * @param endDate   New end date for the iteration.
     * @return The modified instance of the iteration.
     */
    Iteration setDates(final Iteration iteration, final LocalDate startDate, final LocalDate endDate);

}
