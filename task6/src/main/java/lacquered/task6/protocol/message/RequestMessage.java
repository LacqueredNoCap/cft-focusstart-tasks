package lacquered.task6.protocol.message;

public class RequestMessage extends Message {
    private final CommandCode commandCode;
    private final User user;

    public RequestMessage(CommandCode commandCode, User user) {
        super(commandCode.getCommand(), MessageType.SERVER_REQUEST);
        this.commandCode = commandCode;
        this.user = user;
    }

    public CommandCode getCommandCode() {
        return commandCode;
    }

    public User getUser() {
        return user;
    }
}
