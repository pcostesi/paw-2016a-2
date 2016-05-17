package ar.edu.itba.models;

public enum Priority {

	LOW(0, "Low"), 
	NORMAL(1, "Normal"), 
	HIGH(2, "High"), 
	URGENT(3, "Urgent"),
	CRITICAL(4, "Critical");
	
	private final int value;
	private final String label;
	
	Priority(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }
    
    public String toString() {
    	return getLabel();
    }
    
    public String getLabel() {
    	return this.label;
    }
    
    public static Priority getByValue(int value) {
    	switch(value) {
    	case 0:
    		return Priority.LOW;
    	case 1:
			return Priority.NORMAL;
    	case 2:
			return Priority.NORMAL;
    	case 3:
			return Priority.URGENT;
    	case 4:
			return Priority.CRITICAL;
		default:
			return null;
		}
    }
}
