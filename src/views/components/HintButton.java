package src.views.components;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents a button for giving a hint.
 */
public class HintButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("hint.png");
  ImageIcon icon;

  /**
   * Constructs a HintButton.
   */
  public HintButton() {
    ThemeUtils.addThemeListener(this);

    image.setSize(32, 32);

    icon = new ImageIcon(image.getImage());

    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    setCursor(new Cursor(Cursor.HAND_CURSOR));
    addActionListener(e -> {
      GameStatusHandler.hintClicked();
    });

    tooltip = new TranslatedString("redoButtonTooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
