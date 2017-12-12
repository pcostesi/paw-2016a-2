package ar.edu.itba.interfaces.dao;

import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Project;

import java.time.LocalDate;
import java.util.List;

public interface IterationDao {

    Iteration createIteration(final Project project, final int iterationNumber, final LocalDate startDate, final LocalDate endDate);

    void deleteIteration(final Iteration iteration);

    Iteration getIteration(final Project project, final int iterationNumber);

    Iteration getIterationById(final int iterationId);

    boolean iterationExists(final Iteration iteration);

    void updateStartDate(final Iteration iteration, final LocalDate startDate);

    void updateEndDate(final Iteration iteration, final LocalDate endDate);

    List<Iteration> getIterationsForProject(final Project project);

    void increaseNumberOfIterationNumbered(final Project project, final int number);

    void decreaseNumberOfIterationNumbered(final Project project, final int number);

    Project getParent(final Iteration iteration);

    int getMaxNumber(final Project project);

}