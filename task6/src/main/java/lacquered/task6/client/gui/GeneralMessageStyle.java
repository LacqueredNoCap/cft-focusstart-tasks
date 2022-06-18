package lacquered.task6.client.gui;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class GeneralMessageStyle {
    private static final Color DATE_COLOR = Color.BLUE;
    private static final Color USER_NAME_COLOR = Color.GREEN;
    private static final Color CONTENT_COLOR = Color.BLACK;

    private final Style dateStyle;
    private final Style userNameStyle;
    private final Style contentStyle;

    public GeneralMessageStyle(JTextPane messagesTextPane) {
        dateStyle = messagesTextPane.addStyle("dateStyle", null);
        userNameStyle = messagesTextPane.addStyle("userNameStyle", null);
        contentStyle = messagesTextPane.addStyle("contentStyle", null);
        setColors();
    }

    private void setColors() {
        StyleConstants.setForeground(dateStyle, DATE_COLOR);
        StyleConstants.setForeground(contentStyle, CONTENT_COLOR);
        StyleConstants.setForeground(userNameStyle, USER_NAME_COLOR);
    }

    public Style getDateStyle() {
        return dateStyle;
    }

    public Style getContentStyle() {
        return contentStyle;
    }

    public Style getUserNameStyle() {
        return userNameStyle;
    }
}
