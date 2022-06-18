package lacquered.task6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.OptionalInt;
import java.util.Properties;

public class ServerMain {
    private static final Logger log = LoggerFactory.getLogger(ServerMain.class);

    public static final String SERVER_CONFIG_FILE_NAME = "serverConfig.properties";
    public static final int DEFAULT_SERVER_PORT = 2048;

    public static void main(String[] args) {
        new Server(readServerPort().orElse(DEFAULT_SERVER_PORT)).start();
    }

    private static OptionalInt readServerPort() {
        try (InputStream propertiesStream = ServerMain.class
                .getClassLoader()
                .getResourceAsStream(SERVER_CONFIG_FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(propertiesStream);
            String portStr = properties.getProperty("server.port");
            return OptionalInt.of(Integer.parseInt(portStr));
        } catch (IOException | NullPointerException e) {
            log.error("Can't open {}. The port value will be set to default={}",
                    SERVER_CONFIG_FILE_NAME, DEFAULT_SERVER_PORT, e);
        } catch (NumberFormatException e) {
            log.error("Can't get server.port from {}. The port value will be set to default={}",
                    SERVER_CONFIG_FILE_NAME, DEFAULT_SERVER_PORT, e);
        }
        return OptionalInt.empty();
    }
}
