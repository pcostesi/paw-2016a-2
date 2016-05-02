package ar.edu.itba.models;

public class BacklogItem {
	
	private String name;
	private String description;
	final private int backlogItemID;
	
	public BacklogItem(String name, String description, int backlogItemID){
		this.name = name;
		this.description = description;
		this.backlogItemID = backlogItemID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setDescription(String newDescription){
		description = newDescription;
	}
	public int getBacklogItemID(){
		return backlogItemID;
	}
	
}
