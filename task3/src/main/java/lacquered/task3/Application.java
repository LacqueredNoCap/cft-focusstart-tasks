package lacquered.task3;

import lacquered.task3.controller.Controller;
import lacquered.task3.model.GameModel;
import lacquered.task3.records.HighScoresTable;
import lacquered.task3.view.View;

public class Application {

    public static void main(String[] args) {
        HighScoresTable highScoresTable = new HighScoresTable("high-scores-table.data");
        View view = new View(highScoresTable);
        Timer timer = new Timer();
        GameModel model = new GameModel();
        Controller controller = new Controller(model);

        view.setGameTypeListener(controller::setGameType);
        view.setNewGameMenuListener(e -> controller.newGame());
        view.setCellListener(controller::onMouseClick);

        model.addListener(timer);
        model.addListener(view);

        timer.addListener(model::updateScore);
        timer.addListener(view::setTimerValue);

        model.start();
    }
}
