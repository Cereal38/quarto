package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.rules.RulesPage;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

/**
 * A button to open the manual or rules page.
 */
public class ManualButton extends JButton {
  private TranslatedString tooltip;
  private boolean isLightTheme = true;

  // Load icon
  ImageIcon bookImg = ImageUtils.loadImage("book.png", 30, 30);
  ImageIcon bookWhiteImg = ImageUtils.loadImage("book-white.png", 30, 30);

  /**
   * Constructs a new ManualButton.
   */
  public ManualButton() {

    // Add style
    setIcon(bookImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add action
    setActionCommand("Manual");

    addActionListener(e -> {
      EventsHandler.showDialog(new RulesPage(true), true);
    });

    tooltip = new TranslatedString("manualButtonTooltip", this, true);
  }

  /**
   * Updates the icon of the button based on the theme.
   *
   * @param isLightTheme true if the theme is light, false otherwise
   */
  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    if (isLightTheme) {
      setIcon(bookImg);
    } else {
      setIcon(bookWhiteImg);
    }
  }

}
