package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class PauseMenuButton extends JButton {

    public PauseMenuButton() {

        // Load icon
        ImageIcon MenuImg = ImageUtils.loadImage("menu.png", 30, 25);

        // cursor : pointer
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add style
        setIcon(MenuImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(e -> {
        });
    }
}
