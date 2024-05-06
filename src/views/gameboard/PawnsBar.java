package src.views.gameboard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.DimensionUtils;
import src.views.utils.ImageUtils;

public class PawnsBar extends JPanel {

  public PawnsBar() {
    setLayout(new GridLayout(1, 16));

    // Load all pawns
    for (int i = 0; i < 16; i++) {
      add(new Pawn(ImageUtils.getPawn(i), DimensionUtils.getMainFrameWidth() / 16, 50), BorderLayout.CENTER);
    }
  }

}
