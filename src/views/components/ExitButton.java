package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class ExitButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("exit.png");

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
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    addActionListener(e -> {
      EventsHandler.closeApp();
    });

    tooltip = new TranslatedString("quitButtonToolTip", this, true);

  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }

}
