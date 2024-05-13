package src.views.game.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverDialog extends JPanel {
  private String winnerName;

  public GameOverDialog(String winnerName) {
    this.winnerName = winnerName;
    initComponents();
  }

  private void initComponents() {
    JLabel winnerLabel = new JLabel("Winner: " + winnerName);
    JButton newGameButton = new JButton("New game");

    newGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("New game");
      }
    });

    add(winnerLabel);
    add(newGameButton);
  }
}
