package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class ExitButton extends JButton {
  private TranslatedString tooltip;

  // Load
  ImageIcon exitImg = ImageUtils.loadImage("exit.png", 30, 30);
  ImageIcon exitWhiteImg = ImageUtils.loadImage("exit-white.png", 30, 30);

  private boolean isLightTheme = true;

  public ExitButton() {

    // Add style
    setIcon(exitImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    // Add action
    setActionCommand("Quit");

    // set all buttons on cursor : pointer
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    addActionListener(e -> {
      EventsHandler.closeApp();
    });

    tooltip = new TranslatedString("quitButtonToolTip", this, true);

  }

  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    setIcon(isLightTheme ? exitImg : exitWhiteImg);
  }
}
