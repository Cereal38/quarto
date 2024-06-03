package src.views.game.board;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

public class RedoButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("redo.png");

  public RedoButton() {
    ThemeUtils.addThemeListener(this);

    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add style
    image.setSize(32, 32);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.redo();
    });

    tooltip = new TranslatedString("redoButtonTooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
