package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.AudioUtils;
import src.views.utils.ThemeUtils;

public class MusicButton extends JButton implements ThemeListener {

  private boolean isMusicOn;
  private AudioUtils audioUtils;
  ImageThemed onImage = new ImageThemed("music-on.png");
  ImageThemed offImage = new ImageThemed("music-off.png");
  ImageIcon onIcon;
  ImageIcon offIcon;

  public MusicButton() {
    ThemeUtils.addThemeListener(this);

    isMusicOn = false;

    // Add style
    onImage.setSize(30, 30);
    ImageIcon onIcon = new ImageIcon(onImage.getImage());
    offImage.setSize(30, 30);
    ImageIcon offIcon = new ImageIcon(offImage.getImage());
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    setIcon(onIcon);

    // Add action
    setActionCommand("Music");
    addActionListener(e -> {
      isMusicOn = !isMusicOn;
      if (isMusicOn) {
        setIcon(offIcon);
        audioUtils = new AudioUtils();
        audioUtils.loadAudio("assets/music/bg-music.wav"); // Chemin de votre fichier audio
        audioUtils.play();
      } else {
        setIcon(onIcon);
        audioUtils.stop();
      }
    });

    // Add tooltip
    setToolTipText("Toggle Music"); // Vous pouvez utiliser votre TranslatedString ici
  }

  @Override
  public void updatedTheme() {
    if (isMusicOn) {
      offIcon = new ImageIcon(offImage.getImage());
      setIcon(offIcon);
    } else {
      onIcon = new ImageIcon(onImage.getImage());
      setIcon(onIcon);
    }
  }
}
