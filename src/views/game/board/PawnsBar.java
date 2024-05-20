package src.views.game.board;

import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;

public class PawnsBar extends JPanel {

  public PawnsBar() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

    // Get the available pawns
    List<Pawn> pawns = EventsHandler.getController().getAvailablePawns();

    for (Pawn pawn : pawns) {
      add(pawn);
    }

    // Compute the size of the pawns
    // TODO: Move it to gameboard
    int pawnSize = DimensionUtils.getBoardPawnsBarWidth() / 16;
    DimensionUtils.setBarCellSize(pawnSize);

  }

}
