package src.views.components;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class ThemeButton extends JButton {

  private boolean isLightTheme;

  public ThemeButton(ActionListener actionListener) {
    isLightTheme = true;

    // Load icons
    ImageIcon darkImg = ImageUtils.loadImage("dark.png", 30, 30);
    ImageIcon lightImg = ImageUtils.loadImage("light.png", 30, 30);

    // Add style
    setIcon(darkImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

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
      // Call the main action listener
      actionListener.actionPerformed(e);
    });
  }
}
