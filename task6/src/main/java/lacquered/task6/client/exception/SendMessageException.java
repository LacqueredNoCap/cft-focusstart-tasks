package lacquered.task6.client.exception;

public class SendMessageException extends RuntimeException {
    public SendMessageException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}