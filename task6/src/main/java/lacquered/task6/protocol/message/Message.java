package lacquered.task6.protocol.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final String content;
    private final MessageType type;

    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }
}
