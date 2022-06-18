package lacquered.task6.client.event;

public abstract class ClientEvent {
    private final EventType type;

    public ClientEvent(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
