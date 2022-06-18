package lacquered.task3.controller;

import lacquered.task3.model.GameModel;
import lacquered.task3.common.GameType;
import lacquered.task3.view.ButtonType;

public class Controller {
    private final GameModel gameModel;

    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> gameModel.openCell(x, y);
            case MIDDLE_BUTTON -> gameModel.openAdjacentCells(x, y);
            case RIGHT_BUTTON -> gameModel.toggleMarkCell(x, y);
        }
    }

    public void setGameType(GameType gameType) {
        gameModel.setGameType(gameType);
    }

    public void newGame() {
        gameModel.initGame();
    }
}
