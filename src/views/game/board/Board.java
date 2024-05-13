package src.views.game.board;

import java.awt.GridLayout;
import javax.swing.JPanel;
import src.views.listeners.GameStatusListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class Board extends JPanel implements GameStatusListener {

  private Cell[][] cells = new Cell[4][4];

  public Board(int cellSize) {

    // Register this class as a game status listener
    GameStatusHandler.addGameStatusListener(this);

    setLayout(new GridLayout(4, 4));

    refresh();
  }

  public void refresh() {
    removeAll();
    cells = EventsHandler.getController().getTable();
    for (Cell[] line : cells) {
      for (Cell cell : line) {
        if (cell != null) {
          add(cell);
        }
      }
    }
    revalidate();
    repaint();
  }

  @Override
  public void update() {
    refresh();
  }

}
