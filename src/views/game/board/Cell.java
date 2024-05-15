package src.views.game.board;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class Cell extends JPanel {
  private int size = 50; // TODO: Make it dynamic
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
        // Only allow the player to place a pawn during the play phase and if the cell
        // is empty
        if (EventsHandler.getController().isPlayPhase() && !hasPawn()) {
          GameStatusHandler.playShotPlayer(line, column);
          repaint();
        } else {
          System.err.println("Error: The cell is already occupied or the game phase does not allow to play a pawn.");
        }
      }
    });
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