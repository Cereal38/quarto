package src.views.game.board;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.EventsHandler;

public class PawnsBar extends JPanel {

  public PawnsBar(int width, int height) {
    setLayout(new GridLayout(8, 2));

    setPreferredSize(new Dimension(width, height));

    // Get the available pawns
    List<Pawn> pawns = EventsHandler.getController().getAvailablePawns();

    // Display the pawns
    // TODO: Rework this
    int i = 0;
    for (Pawn pawn : pawns) {
      add(new PawnsBarSlot(pawn, i % 2));
      i++;
    }

  }
}
