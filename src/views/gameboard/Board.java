package src.views.gameboard;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class Board extends JPanel {

  private int cellSize;
  private int gap;

  public Board(int cellSize, int gap) {
    this.cellSize = cellSize;
    this.gap = gap;

    setLayout(new GridLayout(4, 4, gap, gap));

    for (int i = 0; i < 16; i++) {
      add(new Cell(cellSize));
    }
  }

}
