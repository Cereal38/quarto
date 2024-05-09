package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class UndoButton extends JButton {
    private TranslatedString tooltip;

    public UndoButton() {

        // Load icon
        ImageIcon undoImg = ImageUtils.loadImage("undo.png", 30, 25);

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add style
        setIcon(undoImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(e -> {
        });

        tooltip = new TranslatedString("undoButtonTooltip", this, true);
    }
}
