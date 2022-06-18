package lacquered.task3.common;

import java.util.List;

public record GameChange(List<Cell> updatedCells,
                         GameType gameType,
                         GameChangeType changeType, int mines, int score) {

    public List<Cell> getUpdatedCells() {
        return updatedCells;
    }

    public GameType getGameType() {
        return gameType;
    }

    public GameChangeType getChangeType() {
        return changeType;
    }

    public int getMines() {
        return mines;
    }

    public int getScore() {
        return score;
    }
}
