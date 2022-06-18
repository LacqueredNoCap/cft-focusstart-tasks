package lacquered.task3.view;

import lacquered.task3.GameChangeListener;
import lacquered.task3.common.Cell;
import lacquered.task3.common.GameChange;
import lacquered.task3.common.GameType;
import lacquered.task3.records.HighScoresTable;
import lacquered.task3.records.PlayerRecord;

import java.awt.event.ActionListener;

public class View implements GameChangeListener {

    private final MainWindow mainWindow;
    private final SettingsWindow settingsWindow;
    private final HighScoresWindow highScoresWindow;

    private GameType gameType;
    private int score;

    private final HighScoresTable highScoresTable;
    private ActionListener newGameListener;

    public View(HighScoresTable table) {
        this.highScoresTable = table;

        mainWindow = new MainWindow();
        settingsWindow = new SettingsWindow(mainWindow);
        highScoresWindow = new HighScoresWindow(mainWindow);
        setHighScores();

        mainWindow.setSettingsMenuListener(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuListener(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuListener(e -> mainWindow.dispose());

        mainWindow.setVisible(true);
    }

    public void setNewGameMenuListener(ActionListener listener) {
        newGameListener = listener;
        mainWindow.setNewGameMenuListener(newGameListener);
    }

    public void setGameTypeListener(GameTypeListener listener) {
        settingsWindow.setGameTypeListener(listener);
    }

    public void setCellListener(CellEventListener listener) {
        mainWindow.setCellListener(listener);
    }

    public void setTimerValue(int value) {
        mainWindow.setTimerValue(value);
    }

    private void setGameFieldSize(int height, int width) {
        mainWindow.createGameField(height, width);
    }

    private void drawCell(Cell cell) {
        switch (cell.getType()) {
            case CLOSED -> mainWindow.setCellImage(cell.getX(), cell.getY(), GameImage.CLOSED);
            case MARKED -> mainWindow.setCellImage(cell.getX(), cell.getY(), GameImage.MARKED);
            case EMPTY, NEAR_MINE ->  mainWindow.setCellImage(cell.getX(), cell.getY(),
                    GameImage.valueOf("NUM_" + cell.getNearMinesNumber()));
            case REVEALED_MINE -> mainWindow.setCellImage(cell.getX(), cell.getY(), GameImage.BOMB);
        }

    }

    private void setHighScores() {
        int novice = GameType.NOVICE.getOrder();
        int medium = GameType.MEDIUM.getOrder();
        int expert = GameType.EXPERT.getOrder();

        if (highScoresTable.getRecord(novice).score() != Integer.MAX_VALUE) {
            highScoresWindow.setNoviceRecord(highScoresTable.getRecord(novice));
        }
        if (highScoresTable.getRecord(medium).score() != Integer.MAX_VALUE) {
            highScoresWindow.setMediumRecord(highScoresTable.getRecord(medium));
        }
        if (highScoresTable.getRecord(expert).score() != Integer.MAX_VALUE) {
            highScoresWindow.setExpertRecord(highScoresTable.getRecord(expert));
        }
    }

    private void showLoseWindow() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setExitListener(e -> {
            mainWindow.dispose();
            System.exit(0);
        });
        loseWindow.setNewGameListener(newGameListener);
        loseWindow.setVisible(true);
    }

    private void showWinWindow() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setExitListener(e -> {
            mainWindow.dispose();
            System.exit(0);
        });
        winWindow.setNewGameListener(newGameListener);
        winWindow.setVisible(true);
    }

    private void showRecordsWindow() {
        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(this::setNewRecord);
        recordsWindow.setVisible(true);
    }

    private void setNewRecord(String name) {
        PlayerRecord record = new PlayerRecord(name.trim(), score);
        highScoresTable.setRecord(record, gameType.getOrder());
        setHighScores();
    }

    @Override
    public void update(GameChange gameChange) {

        gameType = gameChange.getGameType();
        score = gameChange.score();
        gameChange.getUpdatedCells().forEach(this::drawCell);

        switch (gameChange.getChangeType()) {
            case INIT_GAME_FIELD -> {
                setGameFieldSize(gameChange.getGameType().getHeight(),
                        gameChange.getGameType().getWidth());
                mainWindow.setMinesCount(gameChange.getMines());
            }
            case START_GAME, MOVE -> mainWindow.setMinesCount(gameChange.getMines());
            case WIN -> {
                if (highScoresTable.isHighScore(gameChange.score(), gameChange.gameType().getOrder())) {
                    showRecordsWindow();
                }
                else {
                    showWinWindow();
                }
            }
            case LOSE -> showLoseWindow();
        }

    }
}
