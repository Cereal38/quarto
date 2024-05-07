package src.views.game.board;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import src.views.components.Pawn;

public class Cell extends JPanel {
  private int size;
  private Pawn pawn;
  // Can the player click on this cell
  private boolean clickable = true;

  public Cell(int size, Board board) {
    this.size = size;

    if (clickable) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        System.out.println("Cell clicked");
        setPawn(new Pawn("1111", 50, 50, board));
        repaint();
      }
    });
  }

  public void setPawn(Pawn pawn) {
    this.pawn = pawn;
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