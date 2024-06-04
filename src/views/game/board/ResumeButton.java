package src.views.game.board;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.GameStatusHandler;

/**
 * Represents a button for resuming the game.
 */
public class ResumeButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("resume.png");

  /**
   * Constructs a ResumeButton.
   */
  public ResumeButton() {
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add style
    image.setSize(32, 32);
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    addActionListener(e -> {
      GameStatusHandler.resumeGame();
    });

    tooltip = new TranslatedString("resume-tooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
