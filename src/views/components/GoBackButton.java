package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class GoBackButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("back.png");

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
