package src.views.game.board;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;

public class CenterPanelTopBar extends JPanel {

  private Image bgImage;

  public CenterPanelTopBar() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 8, 15));

    TranslatedLabel stateLbl = new TranslatedLabel("");
    JLabel playerLbl = new JLabel();

    // Set the labels
    if (EventsHandler.getController().isSelectionPhase()) {
      stateLbl.setKey("select-pawn");
    } else if (EventsHandler.getController().isPlayPhase()) {
      stateLbl.setKey("play-pawn");
    }
    playerLbl.setText(EventsHandler.getController().getCurrentPlayerName());

    playerLbl.setForeground(Color.BLUE);
    add(playerLbl);
    add(stateLbl);

    // Load background image
    try {
      bgImage = ImageIO.read(new File("assets/images/gameboard-center-top-bar.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }

}
