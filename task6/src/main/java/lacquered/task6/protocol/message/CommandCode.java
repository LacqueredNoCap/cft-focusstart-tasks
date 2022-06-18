package lacquered.task6.protocol.message;

public enum CommandCode {
    EXIT("/exit", false),
    USERS_LIST("/members", true),
    LOGIN("/login", true);

    private final String command;
    private final boolean isNeedToRespond;

    CommandCode(String command, boolean isNeedToRespond) {
        this.command = command;
        this.isNeedToRespond = isNeedToRespond;
    }

    public String getCommand() {
        return command;
    }

    public boolean isNeedToRespond() {
        return isNeedToRespond;
    }
}
