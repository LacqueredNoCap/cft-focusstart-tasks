package lacquered.task6.client.presenter;

import lacquered.task6.protocol.message.CommandCode;

public interface ClientPresenter {
    void start();

    void onConnectEvent(String ipStr, int port);

    void onGeneralMessageEvent(String content);

    void onRequestMessageEvent(CommandCode code, String userName);

    void onRequestMessageEvent(CommandCode code);
}
