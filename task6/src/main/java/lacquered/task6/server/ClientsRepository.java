package lacquered.task6.server;

import lacquered.task6.protocol.message.User;

import java.util.List;

public interface ClientsRepository {

    void registerUser(User user, ClientHandler clientHandler);

    void removeUser(User user);

    void removeUnregisteredConnection(ClientHandler clientHandler);

    List<User> getAllUsers();

    List<ClientHandler> getAllClientHandlers();
}
