package ar.edu.itba.models;

import java.util.Date;

public class IterationDetail {
	
	private final int iterationId;
	private int number;
	private Date startDate;
	private Date endDate;
	
	public IterationDetail(int iterationId, int number, Date startDate, Date endDate){
		this.iterationId = iterationId;
		this.number = number;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getNumber() {
		return number;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getIterationId() {
		return iterationId;
	}

}
