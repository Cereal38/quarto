package src.views.GameBoard;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Board extends JPanel {

  public Board() {
    setLayout(new GridLayout(4, 4));
    for (int i = 0; i < 16; i++) {
      JButton btn = new JButton(); // Pour représenter les cases du plateau
      add(btn);
    }
  }
}
