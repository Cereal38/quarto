package src.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.EventsHandler;
import src.views.components.TranslatedString;

public class GameBoard extends JPanel {
  private JButton btnUndo = new JButton();
  private JButton btnRedo = new JButton();
  private JButton btnPause = new JButton();

  public GameBoard() {
    setLayout(new BorderLayout());

    // Navbar with undo, redo and pause buttons
    JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Undo button
    TranslatedString translatedBtnUndo = new TranslatedString("undo", btnUndo);
    btnUndo.setText(translatedBtnUndo.getText());

    // Redo button
    TranslatedString translatedBtnRedo = new TranslatedString("redo", btnRedo);
    btnRedo.setText(translatedBtnRedo.getText());

    // Pause button
    TranslatedString translatedBtnPause = new TranslatedString("pause", btnPause);
    btnPause.setText(translatedBtnPause.getText());
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
