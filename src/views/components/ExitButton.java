package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class ExitButton extends JButton {

    public ExitButton() {
        // Load
        ImageIcon exitImg = ImageUtils.loadImage("exit.png", 30, 30);

        // Add style
        setIcon(exitImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        // Add action
        setActionCommand("Quit");

        // set all buttons on cursor : pointer
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addActionListener(e -> {
            System.exit(0);
        });
    }
}
