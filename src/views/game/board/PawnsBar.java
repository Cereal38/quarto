package src.views.game.board;

import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.listeners.GameStatusListener;
import src.views.utils.GameStatusHandler;

public class PawnsBar extends JPanel implements GameStatusListener {

  private List<Pawn> pawns;

  public PawnsBar() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // Register this class as a game status listener
    GameStatusHandler.addGameStatusListener(this);

    updatePawns();

    // TODO: Reimplement this
    // int widthPawn = DimensionUtils.getMainFrameWidth() / 16;
    // int heightPawn = widthPawn * 2;

    for (Pawn pawn : pawns) {
      add(pawn);
    }
  }

  public void refresh() {
    removeAll();
    for (Pawn pawn : pawns) {
      add(pawn);
    }
    revalidate();
    repaint();
  }

  public void updatePawns() {
    pawns = GameStatusHandler.getNotPlayedPawns();
  }

  @Override
  public void update() {
    updatePawns();
    refresh();
  }

}
