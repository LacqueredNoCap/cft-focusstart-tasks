package lacquered.task6.client.gui;

import lacquered.task6.client.presenter.ClientPresenter;
import lacquered.task6.protocol.message.GeneralMessage;

public interface ChatView {
    void start();

    void exit();

    void showMessageAboutSuccessConnect();

    void showMessageAboutFailedConnect(String cause);

    void showError(String errorMessage);

    void setClientPresenter(ClientPresenter presenter);

    void showLoginFail(String userName);

    void showUserList(String userList);

    void showGeneralMessage(GeneralMessage message);
}
