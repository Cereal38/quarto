package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class PauseMenuButton extends JButton {
    private TranslatedString tooltip;
    private boolean isLightTheme = true;
    // Load icon
    ImageIcon MenuImg = ImageUtils.loadImage("menu.png", 30, 25);
    ImageIcon MenuWhiteImg = ImageUtils.loadImage("menu-white.png", 30, 25);

    public PauseMenuButton() {

        // cursor : pointer
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add style
        setIcon(MenuImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(e -> {
        });

        tooltip = new TranslatedString("menuButtonTooltip", this, true);
    }

    public void updateIcon(boolean isLightTheme) {
        this.isLightTheme = isLightTheme;
        setIcon(isLightTheme ? MenuImg : MenuWhiteImg);
    }
}
