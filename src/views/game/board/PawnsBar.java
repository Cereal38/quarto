package src.views.game.board;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.EventsHandler;

public class PawnsBar extends JPanel {

  private Image bgImage;

  public PawnsBar(int width, int height, int gap) {
    setLayout(new FlowLayout(FlowLayout.CENTER, gap, gap));
    setPreferredSize(new Dimension(width, height));

    // Get the available pawns
    List<Pawn> pawns = EventsHandler.getController().getAvailablePawns();

    // Display the pawns
    for (Pawn pawn : pawns) {
      add(pawn);
    }

    try {
      // TODO: Load bg only once
      bgImage = ImageIO.read(new File("assets/images/pawns-bar.png"));
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
