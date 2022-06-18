package lacquered.task6.client.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lacquered.task6.client.exception.ServerConnectionException;
import lacquered.task6.protocol.message.*;
import lacquered.task6.client.Client;
import lacquered.task6.client.ClientObserver;
import lacquered.task6.client.event.ClientEvent;
import lacquered.task6.client.event.EventType;
import lacquered.task6.client.event.NewMessageEvent;
import lacquered.task6.client.exception.SendMessageException;
import lacquered.task6.client.gui.ChatView;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatClientPresenter implements ClientPresenter, ClientObserver {
    private static final Logger log = LoggerFactory.getLogger(ChatClientPresenter.class);

    private final Client client;
    private final ChatView view;

    public ChatClientPresenter(Client client, ChatView view) {
        this.client = client;
        this.view = view;
    }

    @Override
    public void start() {
        client.addObserver(this);
        view.setClientPresenter(this);
        view.start();
    }

    @Override
    public void onRequestMessageEvent(CommandCode code) {
        onRequestMessageEvent(code, null);
    }

    @Override
    public void onGeneralMessageEvent(String content) {
        try {
            client.sendGeneralMessage(content);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        } catch (IllegalStateException e) {
            log.warn(e.getMessage());
            view.showError(e.getMessage());
        } catch (SendMessageException e) {
            log.error(e.getMessage());
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onRequestMessageEvent(CommandCode code, String userName) {
        try {
            if (code == CommandCode.LOGIN && userName != null) {
                client.sendLoginMessage(userName);
            } else {
                client.sendRequestMessage(code);
            }
        } catch (SendMessageException e) {
            log.warn("Error while send request", e);
            view.showError(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("User name=\"{}\" is not valid", userName, e);
            view.showError(e.getMessage());
        } catch (IllegalStateException e) {
            log.warn("User is not logged", e);
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onConnectEvent(String host, int port) {
        try {
            InetAddress address = InetAddress.getByName(host);
            client.connectToServer(address, port);
            client.start();
            view.showMessageAboutSuccessConnect();
        } catch (UnknownHostException e) {
            log.error("Invalid ip={}", host, e);
            view.showMessageAboutFailedConnect("Invalid ip=" + host);
        } catch (ServerConnectionException e) {
            log.error("Connect to server={}:{} failed", host, port, e);
            view.showMessageAboutFailedConnect(String.format("Connect to server=%s:%d failed", host, port));
        } catch (IllegalStateException e) {
            log.warn("User already connected", e);
            view.showMessageAboutFailedConnect("User already connected");
        }
    }

    @Override
    public void update(ClientEvent event) {
        if (event.getType() == EventType.EXIT) {
            view.exit();
        } else if (event.getType() == EventType.NEW_MESSAGE) {
            NewMessageEvent newMessageEvent = (NewMessageEvent) event;
            handleReceivingNewMessage(newMessageEvent.getMessage());
        } else {
            throw new IllegalStateException("Unknown event type");
        }
    }

    private void handleReceivingNewMessage(Message message) {
        MessageType messageType = message.getType();
        if (messageType == MessageType.SERVER_REQUEST) {
            throw new IllegalStateException("User can't work with requests");
        } else if (messageType == MessageType.GENERAL_MESSAGE) {
            view.showGeneralMessage((GeneralMessage) message);
        } else if (messageType == MessageType.SERVER_RESPONSE) {
            handleReceivingResponse((ResponseMessage) message);
        }
    }

    private void handleReceivingResponse(ResponseMessage message) {
        if (message.getResponseStatusCode() == ResponseStatusCode.SUCCESS) {
            handleSuccessResponse(message);
        } else if (message.getResponseStatusCode() == ResponseStatusCode.FAILURE) {
            handleFailureResponse(message);
        }

    }

    private void handleFailureResponse(ResponseMessage message) {
        RequestMessage requestMessage = message.getRequestMessage();
        if (requestMessage.getCommandCode() == CommandCode.LOGIN) {
            view.showLoginFail(requestMessage.getUser().name());
        }
    }

    private void handleSuccessResponse(ResponseMessage message) {
        RequestMessage requestMessage = message.getRequestMessage();
        if (requestMessage.getCommandCode() == CommandCode.USERS_LIST) {
            view.showUserList(message.getContent());
        }
    }
}
