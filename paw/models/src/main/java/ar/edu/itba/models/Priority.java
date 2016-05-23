package ar.edu.itba.models;

public enum Priority {

	LOW(0, "LOW"), 
	NORMAL(1, "NORMAL"), 
	HIGH(2, "HIGH"), 
	URGENT(3, "URGENT"),
	CRITICAL(4, "CRITICAL");
	
	private final int value;
	private final String label;
	
	Priority(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }
    
    public String getLabel() {
    	return this.label;
    }
    
}
