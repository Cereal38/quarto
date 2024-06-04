package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

/**
 * A button used for navigating back to the previous page.
 * <p>
 * This button provides functionality to navigate back to the previous page and
 * displays an icon for visual indication. It also supports displaying a
 * tooltip.
 */
public class GoBackButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("back.png");

  /**
   * Constructs a new GoBackButton.
   * <p>
   * This constructor initializes the button with the appropriate icon and adds an
   * action listener to handle navigation to the previous page.
   */
  public GoBackButton() {
    ThemeUtils.addThemeListener(this);
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add style
    image.setSize(24, 24);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      // Navigate back to the previous page
      EventsHandler.navigate("MainMenu");
    });

    tooltip = new TranslatedString("backButtonTooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
