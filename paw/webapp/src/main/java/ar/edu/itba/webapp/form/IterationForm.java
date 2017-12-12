package ar.edu.itba.webapp.form;

import ar.edu.itba.webapp.form.constraint.DateRange;
import ar.edu.itba.webapp.form.constraint.DateRangeFree;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@DateRange(start = "beginDate", end = "endDate")
@DateRangeFree(start = "beginDate", end = "endDate")
public class IterationForm {

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate beginDate;

    private String duration;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    private boolean inheritIteration;

    private int iterationNumberToInherit;

    private int projectId;
    private int iterationId;

    public int getIterationId() {
        return iterationId;
    }

    public void setIterationId(final int iterationId) {
        this.iterationId = iterationId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(final int projectId) {
        this.projectId = projectId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(final LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean getInheritIteration() {
        return inheritIteration;
    }

    public void setInheritIteration(final boolean inheritIteration) {
        this.inheritIteration = inheritIteration;
    }

    public int getIterationNumberToInherit() {
        return iterationNumberToInherit;
    }

    public void setIterationNumberToInherit(final int iterationNumberToInherit) {
        this.iterationNumberToInherit = iterationNumberToInherit;
    }

}
