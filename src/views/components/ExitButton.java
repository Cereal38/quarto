package src.views.components;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

/**
 * A button used for exiting an application.
 * <p>
 * This button provides functionality for exiting the application when clicked.
 * It displays an exit icon and sets a tooltip for indicating its purpose.
 */

public class ExitButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("exit.png");

  /**
   * Constructs a new ExitButton.
   * <p>
   * This constructor initializes the button with the exit icon, sets its
   * appearance, adds an action listener to handle the exit action, and sets a
   * tooltip to indicate its purpose.
   */
  public ExitButton() {
    ThemeUtils.addThemeListener(this);

    // Add style
    image.setSize(32, 32);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    // Add action
    setActionCommand("Quit");

    // set all buttons on cursor : pointer
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    addActionListener(e -> {
      EventsHandler.closeApp();
    });

    tooltip = new TranslatedString("quitButtonToolTip", this, true);

  }

  /**
   * Updates the icon of the button based on the theme.
   */
  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }

}
