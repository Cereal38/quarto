package src.views.game.board;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.DimensionUtils;
import src.views.utils.ImageUtils;

public class PawnsBar extends JPanel {

  // All pawns in the game. null means the pawn is already placed on the board
  private Pawn[] pawns = new Pawn[16];

  public PawnsBar() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    int widthPawn = DimensionUtils.getMainFrameWidth() / 16;
    int heightPawn = widthPawn * 2;

    for (int i = 0; i < 16; i++) {
      pawns[i] = new Pawn(ImageUtils.getPawn(i), widthPawn, heightPawn, this);
      if (pawns[i] != null) {
        add(pawns[i]);
      }
    }
  }

  public void refresh() {
    removeAll();
    for (int i = 0; i < 16; i++) {
      if (!pawns[i].isPlayed()) {
        add(pawns[i]);
      }
    }
    revalidate();
    repaint();
  }
}
