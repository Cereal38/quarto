package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.rules.RulesPage;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

/**
 * A button to open the manual or rules page.
 */
public class ManualButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("book.png");

  /**
   * Constructs a new ManualButton.
   */
  public ManualButton() {
    ThemeUtils.addThemeListener(this);

    // Add style
    image.setSize(32, 32);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
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
   */
  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }

}
