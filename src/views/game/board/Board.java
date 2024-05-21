package src.views.game.board;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import src.views.utils.EventsHandler;

public class Board extends JPanel {

  private Cell[][] cells = new Cell[4][4];

  public Board(int width, int height, int cellSize) {

    setLayout(new GridLayout(4, 4));
    setPreferredSize(new Dimension(width, height));

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

}
