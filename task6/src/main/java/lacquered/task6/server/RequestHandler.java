package lacquered.task6.server;

import lacquered.task6.protocol.SerializableObjectsConnection;
import lacquered.task6.server.exception.InvalidUserNameException;
import lacquered.task6.protocol.message.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.StringJoiner;

public class RequestHandler implements Runnable, ClientHandler {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Object writeToClientLock = new Object();

    private final Socket socket;
    private final ClientsRepository clientsRepository;

    private User currentUser;
    private SerializableObjectsConnection connection;

    public RequestHandler(Socket socket, ClientsRepository clientsRepository) {
        this.socket = socket;
        this.clientsRepository = clientsRepository;
    }

    private boolean isUserLogged() {
        return currentUser != null;
    }

    @Override
    public void run() {
        try {
            connection = new SerializableObjectsConnection(socket);
            while (true) {
                Message message = (Message) connection.readObject();
                handleMessage(message);
                log.info("Get message=\"{}\", type={}", message.getContent(), message.getType());
            }
        } catch (IOException e) {
            log.error("Error while reading", e);
            closeUserSession();
        } catch (ClassNotFoundException e) {
            log.error("Can't serialize message", e);
            closeUserSession();
        }
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case SERVER_REQUEST -> handleRequest((RequestMessage) message);
            case GENERAL_MESSAGE -> handleGeneralMessage((GeneralMessage) message);
            default -> throw new IllegalStateException("Can't recognize message type or get server response message");
        }
    }

    private void closeUserSession() {
        try {
            connection.close();
        } catch (IOException ignore) {}

        removeFromRepository();
        if (isUserLogged()) {
            notifyAllAboutUserDisconnect(currentUser);
        }
    }

    private void handleGeneralMessage(GeneralMessage message) {
        clientsRepository
                .getAllClientHandlers()
                .forEach(clientHandler -> clientHandler.sendMessage(message));
    }

    private void handleRequest(RequestMessage message) {
        final CommandCode commandCode = message.getCommandCode();
        if (!isUserLogged() && commandCode != CommandCode.LOGIN) {
            throw new IllegalStateException("Can't receive message before login");
        }
        ResponseStatusCode statusCode = ResponseStatusCode.SUCCESS;
        String usersList = null;
        switch (commandCode) {
            case LOGIN -> statusCode = loginUser(message);
            case EXIT -> closeUserSession();
            case USERS_LIST -> usersList = generateUserListString();
        }
        if (commandCode.isNeedToRespond()) {
            sendResponse(usersList, message, statusCode);
        }
    }

    private void removeFromRepository() {
        if (currentUser == null) {
            clientsRepository.removeUnregisteredConnection(this);
        } else {
            clientsRepository.removeUser(currentUser);
        }
    }

    private void sendResponse(String content, RequestMessage requestMessage, ResponseStatusCode statusCode) {
        ResponseMessage message = content == null
                ? new ResponseMessage(requestMessage, statusCode)
                : new ResponseMessage(content, requestMessage, statusCode);
        sendMessage(message);
    }

    private String generateUserListString() {
        StringJoiner joiner = new StringJoiner("\n");
        clientsRepository.getAllUsers()
                .forEach(user -> joiner.add(user.toString()));
        return joiner.toString();
    }

    private ResponseStatusCode loginUser(RequestMessage message) {
        try {
            clientsRepository.registerUser(message.getUser(), this);
            currentUser = message.getUser();
            notifyAllAboutNewUser(currentUser);
            return ResponseStatusCode.SUCCESS;
        } catch (InvalidUserNameException e) {
            log.warn("User \"{}\" already exists", message.getUser(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Registration problem", e);
        }
        return ResponseStatusCode.FAILURE;
    }

    private void notifyAllAboutNewUser(User user) {
        String content = user.name() + " has joined the chat";
        handleGeneralMessage(new GeneralMessage(content, user));
    }

    private void notifyAllAboutUserDisconnect(User user) {
        String content = user.name() + " has left the chat";
        handleGeneralMessage(new GeneralMessage(content, user));
    }

    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (writeToClientLock) {
                connection.sendObject(message);
            }
        } catch (IOException e) {
            log.error("Can't send message=\"{}\"", message, e);
        }
    }
}