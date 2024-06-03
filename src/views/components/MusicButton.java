package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

/**
 * A button to toggle music on or off.
 */
public class MusicButton extends JButton {

    private boolean isMusicOn;
    private TranslatedString tooltip;

    private boolean isLightTheme = true;

    // Load icons
    ImageIcon musicOnImg = ImageUtils.loadImage("music-on.png", 30, 30);
    ImageIcon musicOffImg = ImageUtils.loadImage("music-off.png", 30, 30);

    // dark theme icons
    ImageIcon musicOnWhiteImg = ImageUtils.loadImage("music-on-white.png", 30, 30);
    ImageIcon musicOffWhiteImg = ImageUtils.loadImage("music-off-white.png", 30, 30);

    /**
     * Constructs a new MusicButton.
     */
    public MusicButton() {
        isMusicOn = true;

        // Add style
        setIcon(musicOnImg);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Add action
        setActionCommand("Music");
        addActionListener(e -> {
            isMusicOn = !isMusicOn;
            updateIcon(isLightTheme);
        });

        // Add tooltip
        tooltip = new TranslatedString("musicButtonTooltip", this, true);
        setToolTipText(tooltip.getText());
    }

    /**
     * Updates the icon of the button based on the theme.
     *
     * @param isLightTheme true if the theme is light, false otherwise
     */
    public void updateIcon(boolean isLightTheme) {
        this.isLightTheme = isLightTheme;
        if (isMusicOn) {
            setIcon(isLightTheme ? musicOnImg : musicOnWhiteImg);
        } else {
            setIcon(isLightTheme ? musicOffImg : musicOffWhiteImg);
        }
    }

}
