package src.views.game.board;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.TranslatedString;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class PauseButton extends JButton {
  private TranslatedString tooltip;
  private boolean isLightTheme = true;

  // Load icon
  ImageIcon pauseImg = ImageUtils.loadImage("pause.png", 32, 32);
  ImageIcon pauseWhiteImg = ImageUtils.loadImage("pause.png", 32, 32);

  public PauseButton() {

    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add style
    setIcon(pauseImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.pauseGame();
    });

    tooltip = new TranslatedString("pause-tooltip", this, true);
  }

  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    setIcon(isLightTheme ? pauseImg : pauseWhiteImg);
  }
}
