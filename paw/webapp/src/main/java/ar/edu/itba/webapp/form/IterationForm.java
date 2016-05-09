package ar.edu.itba.webapp.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ar.edu.itba.webapp.form.constraint.DateRange;

@DateRange(first="beginDate", second="endDate")
public class IterationForm {
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")	
	private LocalDate beginDate;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate endDate;

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

}
