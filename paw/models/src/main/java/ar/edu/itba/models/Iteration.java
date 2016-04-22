package ar.edu.itba.models;

import java.util.Date;

public class Iteration{
	
	private final int iterationId;
	private int number;
	private Date begintDate;
	private Date endDate;
	
	public Iteration(int iterationId, int number, Date begintDate, Date endDate){
		this.iterationId = iterationId;
		this.number = number;
		this.begintDate = begintDate;
		this.endDate = endDate;
	}

	public int getNumber() {
		return number;
	}

	public Date getBeginDate() {
		return begintDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getIterationId() {
		return iterationId;
	}
	
	public void setBeginDate(Date beginDate) {
		this.begintDate = beginDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;		
	}

	public String toString() {
		return "Iteration [iterationId=" + iterationId + ", number=" + number + ", startDate=" + begintDate
				+ ", endDate=" + endDate + "]";
	}

}
