package ar.edu.itba.models;

public enum TaskScore {

	VERY_EASY(0, "Very easy"), 
	EASY(1, "Easy"), 
	NORMAL(2, "Normal"), 
	HARD(4, "Hard"),
	EPIC(8, "Epic");
	
	private final int value;
	private final String label;
	
	private TaskScore(int value, String label) {
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
    
    public final static TaskScore getByValue(int value) {
    	switch(value) {
    	case 0:
    		return TaskScore.VERY_EASY;
    	case 1:
			return TaskScore.EASY;
    	case 2:
			return TaskScore.NORMAL;
    	case 4:
			return TaskScore.HARD;
    	case 8:
			return TaskScore.EPIC;
		default:
			return null;
		}
    }
}
