package src.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameBoard extends JPanel {
  private JButton btnUndo;
  private JButton btnRedo;
  private JButton btnPauseMenu;

  public GameBoard(ActionListener actionListener) {
    setLayout(new BorderLayout());

    // Navbar avec les boutons d'undo, redo, et pause
    JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    btnUndo = new JButton("Undo");
    btnRedo = new JButton("Redo");
    btnPauseMenu = new JButton("Pause Menu");

    navbar.add(btnUndo);
    navbar.add(btnRedo);
    navbar.add(btnPauseMenu);

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

    // Ajout des action listeners
    btnUndo.addActionListener(actionListener);
    btnRedo.addActionListener(actionListener);
    btnPauseMenu.addActionListener(actionListener);
  }
}
