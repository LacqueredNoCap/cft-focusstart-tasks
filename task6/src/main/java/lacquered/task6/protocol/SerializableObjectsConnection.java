package lacquered.task6.protocol;

import java.io.*;
import java.net.Socket;

public class SerializableObjectsConnection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream writer;
    private final ObjectInputStream reader;

    public SerializableObjectsConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.reader = new ObjectInputStream(socket.getInputStream());
    }

    public void sendObject(Serializable obj) throws IOException {
        writer.writeObject(obj);
        writer.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return reader.readObject();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
