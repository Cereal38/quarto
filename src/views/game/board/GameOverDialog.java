package src.views.game.board;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;

public class GameOverDialog extends JPanel {
  private String winnerName;
  private CustomizedButton btnBack = new CustomizedButton("back-to-game");
  private CustomizedButton btnMenu = new CustomizedButton("main-menu");
  private TranslatedLabel winnerLabel = new TranslatedLabel("winner-is");
  private Image bgImage;

  public GameOverDialog(String winnerName) {
    this.winnerName = winnerName;
    btnMenu.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EventsHandler.navigate("MainMenu");
        EventsHandler.hideDialog();
      }
    });

    btnBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EventsHandler.hideDialog();
      }
    });

    // TODO: Beautify the dialog
    add(winnerLabel);
    add(new JLabel(winnerName));
    add(btnBack);
    add(btnMenu);

    try {
      bgImage = ImageIO.read(new File("assets/images/squared-background.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}
