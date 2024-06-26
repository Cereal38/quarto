package src.views.components;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtils;

/**
 * A button to toggle between light and dark themes.
 */
public class ThemeButton extends JButton implements ThemeListener {

  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("brush.png");

  /**
   * Constructs a new ThemeButton.
   */
  public ThemeButton() {
    ThemeUtils.addThemeListener(this);

    // Add style
    image.setSize(28, 28);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add action
    setActionCommand("DarkMode");

    addActionListener(e -> {
      ThemeUtils.toggleTheme();
    });

    // Add tooltip
    tooltip = new TranslatedString("themeButtonTooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
