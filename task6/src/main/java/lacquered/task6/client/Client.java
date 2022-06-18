package lacquered.task6.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lacquered.task6.client.exception.ServerConnectionException;
import lacquered.task6.protocol.message.*;
import lacquered.task6.client.event.ClientEvent;
import lacquered.task6.client.event.ExitEvent;
import lacquered.task6.client.event.NewMessageEvent;
import lacquered.task6.client.exception.SendMessageException;
import lacquered.task6.protocol.SerializableObjectsConnection;
import lacquered.task6.protocol.utils.PortValidator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements ClientObservable {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private final List<ClientObserver> clientObservers;
    private Thread clientThread;

    private User currentUser;
    private SerializableObjectsConnection connection;

    public Client() {
        this.clientObservers = new ArrayList<>();
        this.currentUser = null;
    }

    public void connectToServer(InetAddress ip, int port) {
        PortValidator.validate(port);
        try {
            Socket client = new Socket(ip, port);
            connection = new SerializableObjectsConnection(client);
        } catch (IOException e) {
            log.error("Can't connect to server={}:{}", ip, port, e);
            throw new ServerConnectionException("Connection failed", e);
        }
    }

    public void sendGeneralMessage(String content) {
        if (!isUserLogged()) {
            throw new IllegalStateException("User can't send message until login");
        }
        validateGeneralMessage(content);
        sendMessage(new GeneralMessage(content, currentUser));
    }

    private void validateGeneralMessage(String content) {
        if (content.trim().length() == 0) {
            throw new IllegalArgumentException("Blank message is not allowed");
        }
    }

    public void sendLoginMessage(String userName) {
        validateUserName(userName);
        sendRequestMessage(CommandCode.LOGIN, new User(userName));
    }

    private void validateUserName(String userName) {
        if (userName.length() > 20) {
            throw new IllegalArgumentException("User name must be less than 20 characters");
        }
    }

    public void sendRequestMessage(CommandCode code) {
        if (!isUserLogged()) {
            throw new IllegalStateException("Can't send message from not logged user");
        }
        sendRequestMessage(code, currentUser);
    }

    public boolean isUserLogged() {
        return currentUser != null;
    }

    private void sendRequestMessage(CommandCode code, User user) {
        sendMessage(new RequestMessage(code, user));
        if (code == CommandCode.EXIT) {
            notifyObservers(new ExitEvent());
            close();
        }
    }

    private void sendMessage(Message message) {
        try {
            connection.sendObject(message);
        } catch (IOException e) {
            throw new SendMessageException("Can't send message=\"" + message + "\"", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            log.error("Error while closing connection", e);
        }
    }

    public void start() {
        if (clientThread != null && clientThread.isAlive()) {
            return;
        }
        clientThread = new Thread(this::receiveMessages);
        clientThread.start();
    }

    private void receiveMessages() {
        try {
            while (true) {
                Message message = (Message) connection.readObject();
                checkLoginStatus(message);
                notifyObservers(new NewMessageEvent(message));
            }
        } catch (IOException | ClassNotFoundException e) {
            close();
            log.error("Can't receive message, client=\"{}\" left", currentUser, e);
        }
    }

    private void checkLoginStatus(Message message) {
        if (currentUser != null) {
            return;
        }
        if (message.getType() == MessageType.SERVER_RESPONSE) {
            ResponseMessage response = (ResponseMessage) message;
            if (response.getRequestMessage().getCommandCode() == CommandCode.LOGIN
                    && response.getResponseStatusCode() == ResponseStatusCode.SUCCESS) {
                currentUser = response.getRequestMessage().getUser();
            }
        }
    }

    @Override
    public void notifyObservers(ClientEvent event) {
        for (ClientObserver clientObserver : clientObservers) {
            clientObserver.update(event);
        }
    }

    @Override
    public void addObserver(ClientObserver clientObserver) {
        clientObservers.add(clientObserver);
    }

    @Override
    public void removeObserver(ClientObserver clientObserver) {
        clientObservers.remove(clientObserver);
    }
}
