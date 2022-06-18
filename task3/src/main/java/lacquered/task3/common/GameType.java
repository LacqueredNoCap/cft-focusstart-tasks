package lacquered.task3.common;

public enum GameType {
    NOVICE(0, 10, 9, 9),
    MEDIUM(1, 40, 16, 16),
    EXPERT(2, 99, 16, 30);

    private final int order;
    private final int minesNumber;
    private final int height;
    private final int width;

    GameType(int order, int bombCount, int height, int width) {
        this.order = order;
        this.minesNumber = bombCount;
        this.height = height;
        this.width = width;
    }

    public int getOrder() {
        return order;
    }

    public int getMinesNumber() {
        return minesNumber;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return name() + " (" + minesNumber + " mines, " + width + "x" + height + ")";
    }
}