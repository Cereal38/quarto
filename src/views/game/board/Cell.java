package src.views.game.board;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
  private Pawn ghostPawn;
  private boolean hovered;

  public Cell(Pawn pawn, int line, int column) {
    setOpaque(false);

    this.line = line;
    this.column = column;
    this.pawn = pawn;

    if (!hasPawn()) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    if (hasPawn()) {
      add(pawn);
    }

    // Get the selected pawn to display it at a ghost on hover
    int size = DimensionUtils.getBoardCellSize();
    String selectedPawn = EventsHandler.getController().getSelectedPawnStr();
    if (selectedPawn != null) {
      ghostPawn = new Pawn(selectedPawn, Pawn.NOT_PLAYED, size, size);
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

      public void mouseEntered(MouseEvent e) {
        hovered = true;
        repaint();
      }

      public void mouseExited(MouseEvent e) {
        hovered = false;
        repaint();
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (hovered && ghostPawn != null && canPlay()) {
      Graphics2D g2d = (Graphics2D) g;

      // Draw the ghost pawn with 50% opacity
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      g2d.drawImage(ghostPawn.getImage(), 22, 5, null);
    }
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

}