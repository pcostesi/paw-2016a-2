package ar.edu.itba.models.project;

public enum ProjectStatus {
	
	OPEN(0),
	CLOSED(1);
	
	private final int value;
	
    ProjectStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static ProjectStatus getByValue(int value) {
    	switch(value) {
    	case 0:
    		return ProjectStatus.OPEN;
	    case 1:
			return ProjectStatus.CLOSED;
		default:
			return null;
		}
    }

}
