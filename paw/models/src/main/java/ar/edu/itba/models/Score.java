package ar.edu.itba.models;

public enum Score {

    VERY_EASY(0, "0 points"),
    EASY(1, "1 points"),
    NORMAL(2, "2 points"),
    HARD(4, "4 points"),
    EPIC(8, "8 points");

    private final int value;
    private final String label;

    Score(final int value, final String label) {
        this.value = value;
        this.label = label;
    }

    public static Score getByValue(final int value) {
        switch (value) {
            case 0:
                return Score.VERY_EASY;
            case 1:
                return Score.EASY;
            case 2:
                return Score.NORMAL;
            case 4:
                return Score.HARD;
            case 8:
                return Score.EPIC;
            default:
                return null;
        }
    }

    public final int getValue() {
        return value;
    }

    public final String toString() {
        return getLabel();
    }

    private String getLabel() {
        return this.label;
    }
}
