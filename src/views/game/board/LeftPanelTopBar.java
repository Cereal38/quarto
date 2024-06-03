package src.views.game.board;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

public class LeftPanelTopBar extends JPanel implements ThemeListener {

  private ImageThemed bgImage = new ImageThemed("gameboard-left-top-bar.png");

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

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }

}
