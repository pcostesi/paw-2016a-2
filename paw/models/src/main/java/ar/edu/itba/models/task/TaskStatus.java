package ar.edu.itba.models.task;

public enum TaskStatus {
	
	NOT_STARTED(0), 
	STARTED(1), 
	BLOCKED(2), 
	COMPLETED(3);
	
	private final int value;
	
	TaskStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static TaskStatus getByValue(int value) {
    	switch(value) {
    	case 0:
    		return TaskStatus.NOT_STARTED;
    	case 1:
			return TaskStatus.STARTED;
    	case 2:
			return TaskStatus.BLOCKED;
    	case 3:
			return TaskStatus.COMPLETED;
		default:
			return null;
		}
    }

}
