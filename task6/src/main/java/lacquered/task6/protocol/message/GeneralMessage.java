package lacquered.task6.protocol.message;

import java.time.LocalDateTime;

public class GeneralMessage extends Message {
    private final User sender;
    private final LocalDateTime sendTime;

    public GeneralMessage(String content, User sender) {
        super(content, MessageType.GENERAL_MESSAGE);
        this.sender = sender;
        this.sendTime = LocalDateTime.now();
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public User getSender() {
        return sender;
    }
}
