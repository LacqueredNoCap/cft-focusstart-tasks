package lacquered.task6.example_echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<Socket> clients = new ArrayList<>();
    private Thread clientProcessingThread;

    public static void main(String[] args) throws IOException {
        new Server().startServer(10101);
    }

    private void startServer(int port) throws IOException {
        clientProcessingThread = new Thread(this::processClients);
        clientProcessingThread.setDaemon(true);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            clientProcessingThread.start();

            while (true) {
                Socket newClient = serverSocket.accept();

                synchronized (clients) {
                    clients.add(newClient);
                }
            }
        } finally {
            closeAllClients();
        }
    }

    private void processClients() {
        while (true) {
            List<Socket> clients;
            synchronized (this.clients) {
                clients = new ArrayList<>(this.clients);
            }

            for (Socket client : clients) {
                try {
                    InputStream inputStream = client.getInputStream();
                    int available = inputStream.available();

                    if (available > 0) {
                        byte[] bytes = inputStream.readNBytes(available);
                        //String answer = "Echo server reply: " +  new String(bytes, StandardCharsets.UTF_8);

                        OutputStream outputStream = client.getOutputStream();
                        outputStream.write(bytes);
                        //outputStream.write(answer.getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    synchronized (this.clients) {
                        this.clients.remove(client);
                    }
                }
            }
        }
    }

    private void closeAllClients() {
        synchronized (clients) {
            for (Socket client : clients) {
                try {
                    client.close();
                } catch (IOException ignore) {}
            }

            clients.clear();
        }
    }
}
