package src.views.game.board;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents a button used for displaying the game history.
 */
public class HistoryButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("history.png");

  /**
   * Constructs a HistoryButton object.
   */
  public HistoryButton() {
    ThemeUtils.addThemeListener(this);

    // Set cursor to hand
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add style
    image.setSize(32, 32);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.pauseGame();
    });

    tooltip = new TranslatedString("historyButton", this, true);
  }

  /**
   * Handles theme updates for the history button.
   */
  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
