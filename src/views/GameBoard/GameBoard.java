package src.views.GameBoard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

  public GameBoard() {
    setLayout(new BorderLayout());

    // Add the topbar
    add(new TopBarGameBoard(), BorderLayout.NORTH);

    JPanel board = new JPanel(new GridLayout(4, 4));
    for (int i = 0; i < 16; i++) {
      JButton btn = new JButton(); // Pour représenter les cases du plateau
      board.add(btn);
    }

    add(board, BorderLayout.CENTER);

    // Barre avec les pièces disponibles (par exemple, une grille de 4x4)
    JPanel pieces = new JPanel(new GridLayout(4, 4));
    for (int i = 0; i < 16; i++) {
      JButton piece = new JButton(); // Pour représenter les pièces
      pieces.add(piece);
    }

    add(pieces, BorderLayout.SOUTH);

  }
}
