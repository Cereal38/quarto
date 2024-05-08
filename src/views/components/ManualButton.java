package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class ManualButton extends JButton {
    private TranslatedString tooltip;
    private boolean isLightTheme = true;

    // Load icon
    ImageIcon bookImg = ImageUtils.loadImage("book.png", 30, 30);
    ImageIcon bookWhiteImg = ImageUtils.loadImage("book-white.png", 30, 30);

    public ManualButton() {

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

    public void updateIcon(boolean isLightTheme) {
        this.isLightTheme = isLightTheme;
        if (isLightTheme) {
            setIcon(bookImg);
        } else {
            setIcon(bookWhiteImg);
        }
    }

}
