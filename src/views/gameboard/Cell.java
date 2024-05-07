package src.views.gameboard;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Cell extends JPanel {
  private int x;
  private int y;
  private int size;

  public Cell(int x, int y, int size) {
    this.x = x;
    this.y = y;
    this.size = size;
  }

  public Cell(int size) {
    this(0, 0, size);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawOval(x, y, size, size);
  }
}