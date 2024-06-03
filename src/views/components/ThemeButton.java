package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;
import src.views.utils.ThemeUtils;

/**
 * A button to toggle between light and dark themes.
 */
public class ThemeButton extends JButton {

  private boolean isLightTheme;
  private TranslatedString tooltip;

  /**
   * Constructs a new ThemeButton.
   */
  public ThemeButton() {
    isLightTheme = ThemeUtils.getTheme() == ThemeUtils.LIGHT;

    // Load icons
    ImageIcon darkImg = ImageUtils.loadImage("dark.png", 30, 30);
    ImageIcon lightImg = ImageUtils.loadImage("light.png", 30, 30);

    // Add style
    setIcon(darkImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add action
    setActionCommand("DarkMode");

    addActionListener(e -> {
      // Change icon
      if (isLightTheme) {
        setIcon(lightImg);
      } else {
        setIcon(darkImg);
      }
      isLightTheme = !isLightTheme;
      ThemeUtils.toggleTheme();
    });

    // Add tooltip
    tooltip = new TranslatedString("themeButtonTooltip", this, true);
  }
}
