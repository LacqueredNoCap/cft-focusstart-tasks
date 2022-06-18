package lacquered.task3.model;

import org.apache.log4j.Logger;

import lacquered.task3.GameChangeListener;
import lacquered.task3.common.*;

import java.util.ArrayList;
import java.util.List;

public class GameModel implements GameEvent {
    private static final Logger log = Logger.getLogger(GameModel.class);

    private final List<GameChangeListener> listeners = new ArrayList<>();
    private final List<Cell> updatedCells = new ArrayList<>();

    private GameType gameType;
    private GameField gameField;
    private boolean isFirstOpen;
    private int closedCells;
    private int mines;
    private int marks;
    private int score;
    private boolean isGameRunning;
    private boolean isLoss;


    // API

    public void initGame() {
        log.debug("Init game");

        gameField = new GameField(
                gameType.getMinesNumber(),
                gameType.getHeight(),
                gameType.getWidth());
        mines = gameField.getNumberOfMines();
        marks = 0;
        score = 0;
        updatedCells.clear();
        closedCells = gameField.getHeight() * gameField.getWidth();
        isFirstOpen = true;
        isGameRunning = false;
        isLoss = false;
        notifyListeners(createGameChange(GameChangeType.INIT_GAME_FIELD));
    }

    public void setGameType(GameType gameType) {
        log.debug("Game type changed to " + gameType.toString());

        this.gameType = gameType;
        initGame();
    }

    public void start() {
        log.debug("Running the model");

        setGameType(GameType.NOVICE);
        initGame();
    }

    // Left Mouse Button
    public void openCell(int x, int y) {
        if (isFirstOpen) {
            opening(x, y);
            return;
        }
        if (!isGameRunning) {
            return;
        }
        openCellWithoutNotification(x, y);
        if (isLoss) {
            lose();
            return;
        }
        notifyListeners(createGameChange(GameChangeType.MOVE));
        checkWin();
    }

    private void opening(int x, int y) {
        log.debug("First open");

        gameField.generateMines(x, y);
        isGameRunning = true;
        isFirstOpen = false;
        openCellWithoutNotification(x, y);
        notifyListeners(createGameChange(GameChangeType.START_GAME));
    }

    private void openCellWithoutNotification(int x, int y) {
        Cell cell = gameField.getCell(x, y);
        if (cell.getType() != CellType.CLOSED) {
            return;
        }

        log.debug(cell + " is opened");

        closedCells--;
        updatedCells.add(cell);
        if (cell.isHiddenMine()) {
            cell.setCellType(CellType.REVEALED_MINE);
            isLoss = true;
            return;
        }
        cell.setNearMinesNumber(gameField.countMinesNearCell(x, y));
        if (cell.getType() == CellType.EMPTY) {
            List<Cell> adjacentCells = gameField.getAdjacentCells(x, y);
            adjacentCells.forEach(adCell -> openCellWithoutNotification(adCell.getX(), adCell.getY()));
        }
    }

    // Right Mouse Button
    public void toggleMarkCell(int x, int y) {
        if (!isGameRunning) {
            return;
        }
        Cell cell = gameField.getCell(x, y);
        if (cell.getType() == CellType.MARKED) {
            log.debug(cell + " is unmarked");

            cell.setCellType(CellType.CLOSED);
            marks--;
            updatedCells.add(cell);
        }
        else {
            markCell(cell);
        }
        notifyListeners(createGameChange(GameChangeType.MOVE));
    }

    private void markCell(Cell cell) {
        if (cell.getType() != CellType.CLOSED ||
                marks == mines) {
            return;
        }

        log.debug(cell + " is marked");

        cell.setCellType(CellType.MARKED);
        marks++;
        updatedCells.add(cell);
    }

    // Scroll Mouse Button
    public void openAdjacentCells(int x, int y) {
        if (!isGameRunning) {
            return;
        }
        Cell cell = gameField.getCell(x, y);
        if (cell.getType() != CellType.NEAR_MINE) {
            return;
        }
        List<Cell> adjacentCells = gameField.getAdjacentCells(x, y);
        int marksNumber = 0;
        for (Cell adCell : adjacentCells) {
            if (adCell.getType() == CellType.MARKED) {
                marksNumber++;
            }
        }
        if (marksNumber != cell.getNearMinesNumber()) {
            return;
        }

        log.debug("Opened adjacent cells of " + cell);

        adjacentCells.forEach(nearCell -> openCellWithoutNotification(nearCell.getX(), nearCell.getY()));
        if (isLoss) {
            lose();
            return;
        }
        notifyListeners(createGameChange(GameChangeType.MOVE));
        checkWin();
    }

    public void updateScore(int score) {
        this.score = score;
    }

    private void lose() {
        log.debug("LOSE");

        isGameRunning = false;
        notifyListeners(createGameChange(GameChangeType.LOSE));
    }

    private void checkWin() {
        if (isGameRunning && closedCells == mines) {
            win();
        }
    }

    private void win() {
        log.debug("WIN");

        isGameRunning = false;
        notifyListeners(createGameChange(GameChangeType.WIN));
    }

    private GameChange createGameChange(GameChangeType changeType) {
        final List<Cell> updatedCellsCopy = new ArrayList<>();
        updatedCells.forEach(cell -> updatedCellsCopy.add(new Cell(cell)));
        updatedCells.clear();
        return new GameChange(
                updatedCellsCopy,
                gameType,
                changeType,
                mines - marks,
                score
        );
    }

    @Override
    public void addListener(GameChangeListener gameObserver) {
        listeners.add(gameObserver);
    }

    @Override
    public void removeListener(GameChangeListener gameObserver) {
        listeners.remove(gameObserver);
    }

    @Override
    public void notifyListeners(GameChange gameChange) {
        listeners.forEach(listener -> listener.update(gameChange));
    }
}
