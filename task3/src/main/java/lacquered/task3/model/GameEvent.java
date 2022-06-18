package lacquered.task3.model;

import lacquered.task3.common.GameChange;
import lacquered.task3.GameChangeListener;

public interface GameEvent {

    void addListener(GameChangeListener gameObserver);

    void removeListener(GameChangeListener gameObserver);

    void notifyListeners(GameChange gameChange);

}
