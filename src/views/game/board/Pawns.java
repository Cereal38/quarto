package src.views.game.board;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Pawns extends JPanel {

  public Pawns() {
    setLayout(new GridLayout(2, 8));
    for (int i = 0; i < 16; i++) {
      JButton btn = new JButton(); // Pour représenter les cases du plateau
      add(btn);
    }
  }

}
