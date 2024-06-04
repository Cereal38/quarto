package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Represents the top bar of the game board.
 */
public class TopBarGameBoard extends JPanel {

  /**
   * Constructs a TopBarGameBoard with the specified width and height.
   *
   * @param width  the width of the top bar
   * @param height the height of the top bar
   */
  public TopBarGameBoard(int width, int height) {
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(width, height));

    // Left panel with undo and redo buttons
    JPanel leftPanel = new LeftPanelTopBar();
    add(leftPanel, BorderLayout.WEST);

    // Right panel with pause button
    JPanel rightPanel = new RightPanelTopBar();
    add(rightPanel, BorderLayout.EAST);

    // Center panel with the current state of the game
    JPanel centerPanel = new CenterPanelTopBar();
    add(centerPanel, BorderLayout.CENTER);
  }
}
