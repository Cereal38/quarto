package src.views.game.board;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class Board extends JPanel implements ThemeListener {

  private Cell[][] cells = new Cell[4][4];
  private ImageThemed boardImage = new ImageThemed("board.png");

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
