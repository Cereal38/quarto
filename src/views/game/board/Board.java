package src.views.game.board;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

/**
 * The game board panel containing cells.
 */
public class Board extends JPanel implements ThemeListener {

  private Cell[][] cells = new Cell[4][4];
  private ImageThemed boardImage = new ImageThemed("board.png");

  /**
   * Constructs a new Board with the specified dimensions and cell size.
   *
   * @param width    the width of the board
   * @param height   the height of the board
   * @param cellSize the size of each cell
   */
  public Board(int width, int height, int cellSize) {

    ThemeUtils.addThemeListener(this);

    setOpaque(false);

    setLayout(new GridLayout(4, 4));
    setPreferredSize(new Dimension(width, height));

    // Add border to fit the background image
    setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 5));

    // Get and add all cells
    cells = EventsHandler.getController().getTable();
    for (Cell[] line : cells) {
      for (Cell cell : line) {
        if (cell != null) {
          add(cell);
        }
      }
    }

  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(boardImage.getImage(), 0, 0, getWidth(), getHeight(), this);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }

}
