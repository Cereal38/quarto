package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class ManualButton extends JButton {
    private TranslatedString tooltip;

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
        });

        tooltip = new TranslatedString("manualButtonTooltip", this, true);
    }

}
