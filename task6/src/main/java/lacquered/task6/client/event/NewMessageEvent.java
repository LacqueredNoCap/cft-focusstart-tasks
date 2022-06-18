package lacquered.task6.client.event;

import lacquered.task6.protocol.message.Message;

public class NewMessageEvent extends ClientEvent {
    private final Message message;

    public NewMessageEvent(Message message) {
        super(EventType.NEW_MESSAGE);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
