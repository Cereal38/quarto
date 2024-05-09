package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import src.views.rules.RulesPage;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class ManualButton extends JButton {

    public ManualButton() {

        // Load icon
        ImageIcon bookImg = ImageUtils.loadImage("book.png", 30, 30);

        // Add style
        setIcon(bookImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add action
        setActionCommand("Manual");

        addActionListener(e -> {
            EventsHandler.showDialog(new RulesPage(true));
        });
    }
}
