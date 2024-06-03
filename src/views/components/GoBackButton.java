package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.TranslatedString;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

/**
 * A button used for navigating back to the previous page.
 * <p>
 * This button provides functionality to navigate back to the previous page
 * and displays an icon for visual indication. It also supports displaying a tooltip.
 */
public class GoBackButton extends JButton {
    private TranslatedString tooltip;
    private boolean isLightTheme = true;

    // Load icon
    ImageIcon backImg = ImageUtils.loadImage("go-back.png", 32, 32);

    /**
     * Constructs a new GoBackButton.
     * <p>
     * This constructor initializes the button with the appropriate icon and adds
     * an action listener to handle navigation to the previous page.
     */
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
