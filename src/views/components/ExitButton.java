package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

/**
 * A button used for exiting an application.
 * <p>
 * This button provides functionality for exiting the application when clicked. It displays
 * an exit icon and sets a tooltip for indicating its purpose.
 */

public class ExitButton extends JButton {
  private TranslatedString tooltip;

  // Load
  ImageIcon exitImg = ImageUtils.loadImage("exit.png", 30, 30);
  ImageIcon exitWhiteImg = ImageUtils.loadImage("exit-white.png", 30, 30);

  private boolean isLightTheme = true;

  /**
   * Constructs a new ExitButton.
   * <p>
   * This constructor initializes the button with the exit icon, sets its appearance, adds
   * an action listener to handle the exit action, and sets a tooltip to indicate its purpose.
   */
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

  /**
   * Updates the icon of the button based on the theme.
   * <p>
   * This method updates the button's icon to either the default exit icon or the light-themed
   * exit icon, depending on the current theme.
   *
   * @param isLightTheme a boolean indicating whether the light theme is active
   */
  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    setIcon(isLightTheme ? exitImg : exitWhiteImg);
  }
}
