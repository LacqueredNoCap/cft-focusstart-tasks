package lacquered.task6.protocol.utils;

public final class PortValidator {

    private PortValidator() {}

    public static void validate(int port) {
        if (port < 0) {
            throw new IllegalArgumentException("Port value must be positive, actual = {"
                    + port + "}");
        }
    }
}