package src.views.load.save;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.TranslatedString;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class GoBackButton extends JButton {
    private TranslatedString tooltip;
    private boolean isLightTheme = true;

    // Load icon
    ImageIcon backImg = ImageUtils.loadImage("go-back.png", 32, 32);

    public GoBackButton() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add style
        setIcon(backImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addActionListener(e -> {
            // Navigate back to the previous page
            EventsHandler.navigate("MainMenu");
        });

        tooltip = new TranslatedString("backButtonTooltip", this, true);
    }
}
