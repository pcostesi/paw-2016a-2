package ar.edu.itba.webapp.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ar.edu.itba.webapp.form.constraint.DateRange;
import ar.edu.itba.webapp.form.constraint.DateRangeFree;

@DateRange(start="beginDate", end="endDate")
@DateRangeFree(start="beginDate", end="endDate")
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

	public void setIterationId(int iterationId) {
		this.iterationId = iterationId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean getInheritIteration() {
		return inheritIteration;
	}

	public void setInheritIteration(boolean inheritIteration) {
		this.inheritIteration = inheritIteration;
	}

	public int getIterationNumberToInherit() {
		return iterationNumberToInherit;
	}

	public void setIterationNumberToInherit(int iterationNumberToInherit) {
		this.iterationNumberToInherit = iterationNumberToInherit;
	}

}
