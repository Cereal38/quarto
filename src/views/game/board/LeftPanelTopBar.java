package src.views.game.board;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import src.views.components.HintButton;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents the top bar on the left side of the game board.
 */
public class LeftPanelTopBar extends JPanel implements ThemeListener {

  private ImageThemed bgImage = new ImageThemed("gameboard-left-top-bar.png");

  /**
   * Constructs a LeftPanelTopBar object.
   */
  public LeftPanelTopBar() {
    ThemeUtils.addThemeListener(this);

    setLayout(new FlowLayout(FlowLayout.LEFT, 12, 14));

    UndoButton btnUndo = new UndoButton();
    RedoButton btnRedo = new RedoButton();

    add(btnUndo);
    add(btnRedo);

    // Only add pause button if not PvP
    if (!GameStatusHandler.isPvP()) {
      // Choose if display pause or resume button
      if (GameStatusHandler.isPaused()) {
        add(new ResumeButton());
      } else {
        add(new PauseButton());
      }
    }

  }

  /**
   * Paints the component with its background image.
   *
   * @param g the graphics context
   */
  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
  }

  /**
   * Handles theme updates for the left panel top bar.
   */
  @Override
  public void updatedTheme() {
    repaint();
  }
}
