package src.views.game.board;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import src.views.components.Field;
import src.views.components.TranslatedString;
import src.views.utils.EventsHandler;

public class CenterPanelTopBar extends JPanel {

  private Image bgImage;
  private Field player1Name;
  private Field player2Name;
  private Field selectionPhase;
  private Field playPhase;

  public CenterPanelTopBar() {
    setLayout(new GridLayout(1, 4));

    String selectionPhaseStr = new TranslatedString("select-pawn").getText();
    String playPhaseStr = new TranslatedString("play-pawn").getText();
    player1Name = new Field("Player 1", true);
    player2Name = new Field("Player 2", false);
    selectionPhase = new Field(selectionPhaseStr, EventsHandler.getController().isSelectionPhase());
    playPhase = new Field(playPhaseStr, EventsHandler.getController().isPlayPhase());

    add(player1Name);
    add(player2Name);
    add(selectionPhase);
    add(playPhase);

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
