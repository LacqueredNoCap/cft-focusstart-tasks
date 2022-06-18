package lacquered.task6.server;

import lacquered.task6.protocol.message.User;
import lacquered.task6.server.exception.InvalidUserNameException;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RequestHandlersRepository implements ClientsRepository {
    private final Map<User, ClientHandler> users = new ConcurrentHashMap<>();
    private final List<ClientHandler> unregisteredHandlers = new CopyOnWriteArrayList<>();

    private boolean isUserLogged(User user) {
        return users.containsKey(user);
    }

    @Override
    public void registerUser(User user, ClientHandler clientHandler) {
        validateRegistration(user, clientHandler);
        unregisteredHandlers.remove(clientHandler);
        users.put(user, clientHandler);
    }

    private void validateRegistration(User user, ClientHandler clientHandler) {
        if (users.containsValue(clientHandler)) {
            throw new IllegalArgumentException("Can't login when user already logged");
        }
        if (!unregisteredHandlers.contains(clientHandler)) {
            throw new IllegalArgumentException("Can't register unknown handler");
        }
        if (isUserLogged(user)) {
            throw new InvalidUserNameException("User \"" + user.name() + "\" already registered");
        }
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public void removeUnregisteredConnection(ClientHandler clientHandler) {
        unregisteredHandlers.remove(clientHandler);
    }

    @Override
    public List<User> getAllUsers() {
        return users.keySet().stream().toList();
    }

    @Override
    public List<ClientHandler> getAllClientHandlers() {
        return users.values().stream().toList();
    }

    public void startNewClientHandle(Socket socket) {
        RequestHandler requestHandler = new RequestHandler(socket, this);
        unregisteredHandlers.add(requestHandler);
        new Thread(requestHandler).start();
    }
}
