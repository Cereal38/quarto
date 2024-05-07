package src.views.gameboard;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class Board extends JPanel {

  public Board(int cellSize) {

    setLayout(new GridLayout(4, 4));

    for (int i = 0; i < 16; i++) {
      add(new Cell(cellSize));
    }
  }

}
