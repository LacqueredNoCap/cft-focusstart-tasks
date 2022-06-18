package lacquered.task6.client;

import lacquered.task6.client.presenter.ChatClientPresenter;
import lacquered.task6.client.presenter.ClientPresenter;
import lacquered.task6.client.gui.ChatView;
import lacquered.task6.client.gui.GUIChatView;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client();
        ChatView view = new GUIChatView();
        ClientPresenter presenter = new ChatClientPresenter(client, view);
        presenter.start();
    }
}
