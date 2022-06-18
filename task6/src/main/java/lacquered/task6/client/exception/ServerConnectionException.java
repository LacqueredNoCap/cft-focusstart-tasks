package lacquered.task6.client.exception;

public class ServerConnectionException extends RuntimeException {
    public ServerConnectionException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}