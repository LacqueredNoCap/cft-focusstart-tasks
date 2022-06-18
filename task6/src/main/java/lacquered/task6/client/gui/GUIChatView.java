package lacquered.task6.client.gui;

import lacquered.task6.client.presenter.ClientPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lacquered.task6.protocol.message.CommandCode;
import lacquered.task6.protocol.message.GeneralMessage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GUIChatView implements ChatView {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss", Locale.getDefault());
    private static final Logger log = LoggerFactory.getLogger(GUIChatView.class);
    public static final String LOGIN_FAIL_OUTPUT_FORMAT = "Name \"%s\" is already in use";

    private final JPanel mainPane;
    private JButton userListButton;
    private JButton sendButton;
    private JTextPane messageInputField;
    private JTextPane messagesListPane;
    private ClientPresenter presenter;
    private JFrame mainWindow;
    private GeneralMessageStyle generalMessageStyle;

    public GUIChatView() {
        mainPane = new JPanel();
        mainPane.setLayout(new GridBagLayout());
        initUserListButton();
        initSendButton();
        initMessageInputField();
        initMessagesListArea();
        arrangeAllElements(userListButton, sendButton, messageInputField, messagesListPane);
    }

    @Override
    public void start() {
        mainWindow = new JFrame("Chat");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setContentPane(mainPane);
        mainWindow.pack();
        showConnectionDialog();
        mainWindow.setVisible(true);
    }

    private void showConnectionDialog() {
        String ip = JOptionPane.showInputDialog("Enter IP");
        String portStr = JOptionPane.showInputDialog("Enter port");
        try {
            presenter.onConnectEvent(ip, Integer.parseInt(portStr));
        } catch (NumberFormatException e) {
            showError("Port must be a number");
            showConnectionDialog();
        }
    }

    private void showLoginDialog() {
        String userName = JOptionPane.showInputDialog("Enter name");
        presenter.onRequestMessageEvent(CommandCode.LOGIN, userName);
    }

    private void arrangeAllElements(JButton userListButton, JButton sendButton, JTextPane messageInputField, JTextPane messagesListArea) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPane.add(sendButton, gbc);

        gbc.gridx = 1;
        mainPane.add(userListButton, gbc);

        final JScrollPane messageInputScrollPane = new JScrollPane();
        messageInputScrollPane.setHorizontalScrollBarPolicy(30);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 10;
        gbc.insets = new Insets(15, 0, 0, 0);
        mainPane.add(messageInputScrollPane, gbc);
        messageInputScrollPane.setViewportView(messageInputField);

        final JScrollPane messagesListScrollPane = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 100;
        mainPane.add(messagesListScrollPane, gbc);
        messagesListScrollPane.setViewportView(messagesListArea);
    }

    private void initMessagesListArea() {
        messagesListPane = new JTextPane();
        messagesListPane.setEditable(false);
        generalMessageStyle = new GeneralMessageStyle(messagesListPane);
    }

    private void initMessageInputField() {
        messageInputField = new JTextPane();
    }

    private void initSendButton() {
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            presenter.onGeneralMessageEvent(messageInputField.getText());
            messageInputField.setText("");
        });
    }

    private void initUserListButton() {
        userListButton = new JButton("Members");
        userListButton.addActionListener(e -> presenter.onRequestMessageEvent(CommandCode.USERS_LIST));
    }

    @Override
    public void showGeneralMessage(GeneralMessage message) {
        String dateStr = dateFormat.format(message.getSendTime());
        String userStr = message.getSender().name();
        StyledDocument doc = messagesListPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), dateStr, generalMessageStyle.getDateStyle());
            doc.insertString(doc.getLength(), " " + userStr, generalMessageStyle.getUserNameStyle());
            doc.insertString(doc.getLength(), ": " + message.getContent() + "\n", generalMessageStyle.getContentStyle());
        } catch (BadLocationException e) {
            log.error("Error while general message output", e);
        }
    }

    @Override
    public void showMessageAboutSuccessConnect() {
        showLoginDialog();
    }

    @Override
    public void exit() {
        mainWindow.setVisible(false);
        mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(mainPane, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setClientPresenter(ClientPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoginFail(String userName) {
        JOptionPane.showMessageDialog(
                mainPane,
                String.format(LOGIN_FAIL_OUTPUT_FORMAT, userName),
                "Login error",
                JOptionPane.ERROR_MESSAGE
        );
        showLoginDialog();
    }

    @Override
    public void showUserList(String userList) {
        JOptionPane.showMessageDialog(
                mainPane,
                userList,
                "Users",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void showMessageAboutFailedConnect(String cause) {
        JOptionPane.showMessageDialog(
                mainPane,
                cause,
                "Connection error",
                JOptionPane.ERROR_MESSAGE
        );
        showConnectionDialog();
    }
}
