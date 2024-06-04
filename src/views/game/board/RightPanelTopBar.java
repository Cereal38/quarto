package src.views.game.board;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents the top bar on the right side of the game board.
 */
public class RightPanelTopBar extends JPanel implements ThemeListener {

  private ImageThemed bgImage = new ImageThemed("gameboard-right-top-bar.png");

  /**
   * Constructs a RightPanelTopBar.
   */
  public RightPanelTopBar() {
    ThemeUtils.addThemeListener(this);

    setLayout(new FlowLayout(FlowLayout.LEFT, 12, 14));

    PauseMenuButton btnPause = new PauseMenuButton();

    // Add action listeners to the buttons
    btnPause.addActionListener(e -> {
      EventsHandler.showDialog(new PauseDialogContent(), true);
    });

    JButton historyButton = new HistoryButton();
    historyButton.addActionListener(e -> {
      // Show the history panel
      EventsHandler.showDialog(new MovesHistoryDialog(), true);
    });
    add(historyButton);
    add(btnPause);

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
