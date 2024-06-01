package src.views.game.board;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.TranslatedString;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class ResumeButton extends JButton {
  private TranslatedString tooltip;
  private boolean isLightTheme = true;

  // Load icon
  ImageIcon resumeImg = ImageUtils.loadImage("resume.png", 32, 32);
  ImageIcon resumeWhiteImg = ImageUtils.loadImage("resume.png", 32, 32);

  public ResumeButton() {

    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add style
    setIcon(resumeImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.resumeGame();
    });

    tooltip = new TranslatedString("resume-tooltip", this, true);
  }

  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    setIcon(isLightTheme ? resumeImg : resumeWhiteImg);
  }
}
