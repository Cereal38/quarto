package src.views.game.board;

import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.listeners.GameStatusListener;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;

public class PawnsBar extends JPanel implements GameStatusListener {

  private List<Pawn> pawns;
  int pawnSize;

  public PawnsBar() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

    // Register this class as a game status listener
    // GameStatusHandler.addGameStatusListener(this);

    updatePawns();
    refresh();

  }

  public void refresh() {
    removeAll();
    for (Pawn pawn : pawns) {
      add(pawn);
    }
    this.pawnSize = DimensionUtils.getBoardPawnsBarWidth() / 16;
    DimensionUtils.setBarCellSize(this.pawnSize);
    revalidate();
    repaint();
  }

  public void updatePawns() {
    pawns = EventsHandler.getController().getAvailablePawns();
  }

  @Override
  public void update() {
    updatePawns();
    refresh();
  }

}
