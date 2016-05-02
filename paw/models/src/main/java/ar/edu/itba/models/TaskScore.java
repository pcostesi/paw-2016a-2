package ar.edu.itba.models;

public enum TaskScore {

	VERY_EASY(0, "Very easy"), 
	EASY(1, "Easy"), 
	NORMAL(2, "Normal"), 
	HARD(4, "Hard"),
	EPIC(8, "Epic");
	
	private final int value;
	private final String label;
	
	TaskScore(int value, String label) {
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
    
    public static TaskScore getByValue(int value) {
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
