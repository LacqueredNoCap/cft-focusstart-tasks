package lacquered.task2.figures;

public enum FigureType {
    CIRCLE(1), RECTANGLE(2), TRIANGLE(3);

    private final int numberOfParams;

    FigureType(int numberOfParams) {
        this.numberOfParams = numberOfParams;
    }

    public int getNumberOfParams() {
        return numberOfParams;
    }
}
