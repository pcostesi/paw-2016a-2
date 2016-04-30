package ar.edu.itba.models;

public enum TaskPriority {

	LOW(0, "Low"), 
	NORMAL(1, "Normal"), 
	HIGH(2, "High"), 
	URGENT(3, "Urgent"),
	CRITICAL(4, "Critical");
	
	private final int value;
	private final String label;
	
	TaskPriority(int value, String label) {
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
    
    public static TaskPriority getByValue(int value) {
    	switch(value) {
    	case 0:
    		return TaskPriority.LOW;
    	case 1:
			return TaskPriority.NORMAL;
    	case 2:
			return TaskPriority.NORMAL;
    	case 3:
			return TaskPriority.URGENT;
    	case 4:
			return TaskPriority.CRITICAL;
		default:
			return null;
		}
    }
}
