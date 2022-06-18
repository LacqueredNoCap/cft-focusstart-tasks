package lacquered.task6.example_echo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final String CHARSET_NAME = "UTF-8";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 10101)) {
            while (true) {
                int available = socket.getInputStream().available();
                if (available > 0) {
                    byte[] bytes = socket.getInputStream().readNBytes(available);
                    System.out.println(new String(bytes, CHARSET_NAME));
                }

                if (System.in.available() > 0) {
                    String data = scanner.next();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(data.getBytes(CHARSET_NAME));
                    outputStream.flush();
                }
            }
        }
    }
}
