package lacquered.task6.client;

import lacquered.task6.client.event.ClientEvent;

public interface ClientObserver {
    void update(ClientEvent event);
}
