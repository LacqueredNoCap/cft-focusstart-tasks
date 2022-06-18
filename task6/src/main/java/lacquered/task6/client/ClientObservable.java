package lacquered.task6.client;

import lacquered.task6.client.event.ClientEvent;

public interface ClientObservable {
    void notifyObservers(ClientEvent event);

    void addObserver(ClientObserver clientObserver);

    void removeObserver(ClientObserver clientObserver);
}
