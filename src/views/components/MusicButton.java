package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;
import src.views.utils.AudioUtils;

public class MusicButton extends JButton {

    private boolean isMusicOn;
    private boolean isLightTheme = true;

    private AudioUtils audioUtils;

    // Load icons
    ImageIcon musicOnImg = ImageUtils.loadImage("music-on.png", 30, 30);
    ImageIcon musicOffImg = ImageUtils.loadImage("music-off.png", 30, 30);

    // dark theme icons
    ImageIcon musicOnWhiteImg = ImageUtils.loadImage("music-on-white.png", 30, 30);
    ImageIcon musicOffWhiteImg = ImageUtils.loadImage("music-off-white.png", 30, 30);

    public MusicButton() {
        isMusicOn = true;
        audioUtils = new AudioUtils();
        audioUtils.loadAudio("assets/music/bg-music.wav"); // Chemin de votre fichier audio

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
            if (isMusicOn) {
                audioUtils.play();
            } else {
                audioUtils.stop();
            }
        });

        // Add tooltip
        setToolTipText("Toggle Music"); // Vous pouvez utiliser votre TranslatedString ici

        // Start music
        audioUtils.play();
    }

    public void updateIcon(boolean isLightTheme) {
        this.isLightTheme = isLightTheme;
        if (isMusicOn) {
            setIcon(isLightTheme ? musicOnImg : musicOnWhiteImg);
        } else {
            setIcon(isLightTheme ? musicOffImg : musicOffWhiteImg);
        }
    }
}
