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
 * Represents a button used to open the pause menu.
 */
public class PauseMenuButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("menu.png");

  /**
   * Constructs a PauseMenuButton.
   */
  public PauseMenuButton() {
    ThemeUtils.addThemeListener(this);

    // cursor : pointer
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add style
    image.setSize(32, 32);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      // Pause the game
      GameStatusHandler.pauseGame();
    });

    tooltip = new TranslatedString("menuButtonTooltip", this, true);
  }

  /**
   * Updates the theme of the button.
   */
  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }

}
