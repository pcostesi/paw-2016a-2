package ar.edu.itba.models;

import java.util.Date;

public class Log{

	private final int logId;
	private Date date;
	private String title;
	private String description;
	private User postedBy;
	
	public Log(int logId, Date date, String title, String description, User postedBy){
		this.logId = logId;
		this.date = date;
		this.title = title;
		this.description = description;
		this.postedBy = postedBy;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public User getPostedBy() {
		return postedBy;
	}
	
	public String toString() {
		return "Log [date=" + date + ", title=" + title + ", postedBy=" + postedBy + "]";
	}

	public int getLogId() {
		return logId;
	}
	
}
