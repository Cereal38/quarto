package src.views.game.board;

import java.awt.AlphaComposite;
import java.awt.Color;
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
  private boolean highlighted;

  public Cell(Pawn pawn, int line, int column, boolean highlighted) {
    setOpaque(false);

    this.line = line;
    this.column = column;
    this.pawn = pawn;
    this.highlighted = highlighted;

    if (!hasPawn() && canPlay() && !GameStatusHandler.isPaused()) {
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
        GameStatusHandler.playShot(line, column);
        repaint();
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

    if (hovered && ghostPawn != null && canPlay() && !GameStatusHandler.isPaused()) {
      Graphics2D g2d = (Graphics2D) g;

      // Draw the ghost pawn with 50% opacity
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      g2d.drawImage(ghostPawn.getImage(), 22, 5, null);
    }

    if (highlighted) {
      // Draw a border around the cell
      g.setColor(Color.YELLOW);
      g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
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