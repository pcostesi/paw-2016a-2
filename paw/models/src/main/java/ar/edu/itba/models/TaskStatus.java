package ar.edu.itba.models;

public enum TaskStatus {
	
	NOT_STARTED(0, "Not started"), 
	STARTED(1, "Started"), 
	COMPLETED(2, "Done");
	
	private final int value;
	private final String label;
	
	TaskStatus(int value, String label) {
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
    
    public static TaskStatus getByValue(int value) {
    	switch(value) {
    	case 0:
    		return TaskStatus.NOT_STARTED;
    	case 1:
			return TaskStatus.STARTED;
    	case 2:
			return TaskStatus.COMPLETED;
		default:
			return null;
		}
    }

}
