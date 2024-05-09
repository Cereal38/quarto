package src.views.game.board;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class Board extends JPanel {

  private Cell[] cells = new Cell[16];

  public Board(int cellSize) {

    setLayout(new GridLayout(4, 4));

    for (int i = 0; i < 16; i++) {
      cells[i] = new Cell(i / 4, i % 4, cellSize, this);
      add(cells[i]);
    }
  }

  public void refresh() {
    removeAll();
    for (int i = 0; i < 16; i++) {
      add(cells[i]);
    }
    revalidate();
    repaint();
  }

}
