package lacquered.task3.common;

public class Cell {
    private final int x;
    private final int y;
    private CellType cellType;
    private int nearMinesNumber;
    private boolean isHiddenMine;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellType = CellType.CLOSED;
        this.nearMinesNumber = 0;
    }

    public Cell(Cell cell){
        x = cell.getX();
        y = cell.getY();
        cellType = cell.getType();
        nearMinesNumber = cell.getNearMinesNumber();
        if (cell.isHiddenMine()) setHiddenMine();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNearMinesNumber(int nearMinesNumber) {
        cellType = nearMinesNumber > 0 ? CellType.NEAR_MINE : CellType.EMPTY;
        this.nearMinesNumber = nearMinesNumber;
    }

    public int getNearMinesNumber() {
        return nearMinesNumber;
    }

    public CellType getType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public boolean isHiddenMine() {
        return isHiddenMine;
    }

    public void setHiddenMine() {
        isHiddenMine = true;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", nearMinesNumber=" + nearMinesNumber +
                ", isHiddenMine=" + isHiddenMine +
                '}';
    }
}