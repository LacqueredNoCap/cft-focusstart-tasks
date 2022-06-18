package lacquered.task6.server.exception;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String errorMessage) {
        super(errorMessage);
    }
}
