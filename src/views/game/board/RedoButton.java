package src.views.game.board;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.TranslatedString;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class RedoButton extends JButton {
  private TranslatedString tooltip;
  private boolean isLightTheme = true;

  // Load icon
  ImageIcon redoImg = ImageUtils.loadImage("redo.png", 30, 25);
  ImageIcon redoWhiteImg = ImageUtils.loadImage("redo-white.png", 30, 25);

  public RedoButton() {

    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add style
    setIcon(redoImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.redo();
    });

    tooltip = new TranslatedString("redoButtonTooltip", this, true);
  }

  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    setIcon(isLightTheme ? redoImg : redoWhiteImg);
  }
}
