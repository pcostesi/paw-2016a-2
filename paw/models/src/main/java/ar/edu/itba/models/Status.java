package ar.edu.itba.models;

public enum Status {

	NOT_STARTED(0, "NOT_STARTED"), 
	STARTED(1, "STARTED"), 
	COMPLETED(2, "COMPLETED");

	private final int value;
	private final String label;

	private Status(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

}
