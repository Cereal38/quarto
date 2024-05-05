package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class RedoButton extends JButton {

    public RedoButton() {

        // Load icon
        ImageIcon redoImg = ImageUtils.loadImage("redo.png", 30, 25);

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add style
        setIcon(redoImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(e -> {
        });
    }
}
