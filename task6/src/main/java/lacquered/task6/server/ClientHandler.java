package lacquered.task6.server;

import lacquered.task6.protocol.message.Message;

public interface ClientHandler {
    void sendMessage(Message message);
}
