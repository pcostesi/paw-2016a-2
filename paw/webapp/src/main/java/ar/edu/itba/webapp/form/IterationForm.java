package ar.edu.itba.webapp.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import ar.edu.itba.webapp.form.constraint.*;

@DateRange(first="beginDate", second="endDate")
public class IterationForm {
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")	
	private Date beginDate;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
