package lacquered.task6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lacquered.task6.protocol.utils.PortValidator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final int port;
    private final RequestHandlersRepository handlersRepository;
    private Thread serverThread;

    public Server(int port) {
        PortValidator.validate(port);
        this.port = port;
        this.handlersRepository = new RequestHandlersRepository();
    }

    public void start() {
        if (serverThread != null && serverThread.isAlive()) {
            return;
        }
        serverThread = new Thread(this::run);
        serverThread.start();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server starting on port={}", port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.info("Client connected {}:{}", clientSocket.getInetAddress(), clientSocket.getPort());
                handlersRepository.startNewClientHandle(clientSocket);
            }
        } catch (IOException e) {
            log.error("Problem with socket", e);
        }
    }
}
