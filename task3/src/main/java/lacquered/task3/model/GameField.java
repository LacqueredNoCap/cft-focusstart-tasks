package lacquered.task3.model;

import lacquered.task3.common.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField {
    private final int numberOfMines;
    private final int height;
    private final int width;

    private final Cell[][] field;

    private final Random random;

    public GameField(int numberOfMines, int height, int width) {
        this.numberOfMines = numberOfMines;
        this.height = height;
        this.width = width;

        this.field = new Cell[height][width];
        fillField();

        this.random = new Random();
    }

    private void fillField() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                field[row][col] = new Cell(col, row);
            }
        }
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int x, int y) {
        Cell cell = null;
        try {
            cell = field[y][x];
        } catch (IndexOutOfBoundsException ignored) {}

        return cell;
    }

    public List<Cell> getAdjacentCells(int x, int y) {
        List<Cell> nearCells = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                try {
                    nearCells.add(field[y + i][x + j]);
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return nearCells;
    }

    public int countMinesNearCell(int x, int y) {
        int numberOfMines = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    numberOfMines += field[y + i][x + j].isHiddenMine() ? 1 : 0;
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return numberOfMines;
    }

    public void generateMines(int x, int y) {
        List<Cell> surroundingCells = getAdjacentCells(x, y);
        surroundingCells.add(getCell(x, y));

        int minesCount = 0;

        while (minesCount < numberOfMines) {
            Cell randomCell = getCell(random.nextInt(width), random.nextInt(height));

            if (!surroundingCells.contains(randomCell) && !randomCell.isHiddenMine()) {
                randomCell.setHiddenMine();
                minesCount++;
            }
        }
    }
}