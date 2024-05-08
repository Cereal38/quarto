package src.views.game.board;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.GameStatusHandler;

public class Cell extends JPanel {
  private int size;
  private Pawn pawn;

  public Cell(int size, Board board) {
    this.size = size;

    if (!hasPawn()) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        // Only allow the player to place a pawn during the play phase and if the cell
        // is empty
        if (GameStatusHandler.isPlayPhase() && !hasPawn()) {
          // setPawn(new Pawn(GameStatusHandler.getSelectedPawn(), 50, 50, board));
          // GameStatusHandler.nextPhase();
          GameStatusHandler.playPawn();
          repaint();
        } else {
          System.err.println("Not a play phase - " + GameStatusHandler.getGamePhaseAsText());
        }
      }
    });
  }

  public void setPawn(Pawn pawn) {
    this.pawn = pawn;
  }

  public boolean hasPawn() {
    return pawn != null;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawOval(0, 0, size, size);
    if (pawn != null) {
      pawn.setBounds(0, 0, size, size);
      pawn.paint(g);
    }
  }
}