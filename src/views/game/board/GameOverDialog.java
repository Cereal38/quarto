package src.views.game.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.TranslatedButton;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;

public class GameOverDialog extends JPanel {
  private String winnerName;
  private TranslatedButton btnMenu = new TranslatedButton("main-menu");
  private TranslatedLabel winnerLabel = new TranslatedLabel("winner-is");

  public GameOverDialog(String winnerName) {
    this.winnerName = winnerName;
    btnMenu.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EventsHandler.navigate("MainMenu");
        EventsHandler.hideDialog();
      }
    });

    // TODO: Beautify the dialog
    add(winnerLabel);
    add(new JLabel(winnerName));
    add(btnMenu);
  }
}