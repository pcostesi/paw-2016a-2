package ar.edu.itba.models;

public enum Status {
	
	NOT_STARTED(0, "Not started"), 
	STARTED(1, "Started"), 
	COMPLETED(2, "Done");
	
	private final int value;
	private final String label;
	
	private Status(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public final int getValue() {
        return value;
    }
    
    public final String toString() {
    	return getLabel();
    }
    
    public final String getLabel() {
    	return this.label;
    }
    
    public final static Status getByValue(int value) {
    	switch(value) {
    	case 0:
    		return Status.NOT_STARTED;
    	case 1:
			return Status.STARTED;
    	case 2:
			return Status.COMPLETED;
		default:
			return null;
		}
    }

}
