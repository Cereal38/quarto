package src.views.game.board;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class Cell extends JPanel {
  private Pawn pawn;
  private int line;
  private int column;

  public Cell(Pawn pawn, int line, int column) {
    this.line = line;
    this.column = column;
    this.pawn = pawn;

    if (!hasPawn()) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (canPlay()) {
          GameStatusHandler.playShot(line, column);
          repaint();
        } else {
          System.err.println("Error: Can't play a pawn right now.");
        }
      }
    });
  }

  public boolean hasPawn() {
    return pawn != null;
  }

  /**
   * Checks if a player can play a pawn in this cell.
   * 
   * @return true if it's possible.
   */
  private boolean canPlay() {
    return EventsHandler.getController().isPlayPhase() && !hasPawn()
        && !EventsHandler.getController().isCurrentPlayerAI();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int size = DimensionUtils.getBoardCellSize();
    if (pawn == null) {
      g.drawOval(0, 0, size, size);
    } else {
      pawn.setBounds(0, 0, size, size);
      pawn.paint(g);
    }

  }
}