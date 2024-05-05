package src.views.GameBoard;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

  public GameBoard() {
    setLayout(new BorderLayout());

    // Add components
    add(new TopBarGameBoard(), BorderLayout.NORTH);
    add(new Board(), BorderLayout.CENTER);
    add(new Pawns(), BorderLayout.SOUTH);

  }
}
