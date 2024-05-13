package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class UndoButton extends JButton {
    private TranslatedString tooltip;
    private boolean isLightTheme = true;

    // Load icon
    ImageIcon undoImg = ImageUtils.loadImage("undo.png", 30, 25);
    ImageIcon undoWhiteImg = ImageUtils.loadImage("undo-white.png", 30, 25);

    public UndoButton() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add style
        setIcon(undoImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(e -> {
        });

        tooltip = new TranslatedString("undoButtonTooltip", this, true);
    }

    public void updateIcon(boolean isLightTheme) {
        this.isLightTheme = isLightTheme;
        setIcon(isLightTheme ? undoImg : undoWhiteImg);
    }
}
