package src.views.game.board;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.EventsHandler;

public class PawnsBar extends JPanel {

  public PawnsBar(int width, int height) {
    setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
    setPreferredSize(new Dimension(width, height));

    // Get the available pawns
    List<Pawn> pawns = EventsHandler.getController().getAvailablePawns();

    // Display the pawns
    for (Pawn pawn : pawns) {
      add(pawn);
    }

  }

}
