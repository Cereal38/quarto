package src.views.GameBoard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.TranslatedButton;
import src.views.utils.EventsHandler;

public class GameBoard extends JPanel {
  private TranslatedButton btnUndo = new TranslatedButton("undo");
  private TranslatedButton btnRedo = new TranslatedButton("redo");
  private TranslatedButton btnPause = new TranslatedButton("pause");

  public GameBoard() {
    setLayout(new BorderLayout());

    // Navbar with undo, redo and pause buttons
    JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Add action listeners to the buttons
    btnPause.addActionListener(e -> {
      EventsHandler.showDialog(new PauseDialogContent());
    });

    navbar.add(btnUndo);
    navbar.add(btnRedo);
    navbar.add(btnPause);

    add(navbar, BorderLayout.NORTH);

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
