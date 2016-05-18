package ar.edu.itba.models;

public enum TaskStatus {
	
	NOT_STARTED(0), 
	STARTED(1), 
	COMPLETED(2);
	
	private final int value;
	
	private TaskStatus(int value) {
        this.value = value;
    }

    public final int getValue() {
        return value;
    }

    public final static TaskStatus getByValue(int value) {
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
