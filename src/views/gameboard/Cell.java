package src.views.gameboard;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Cell extends JPanel {
  private int size;

  public Cell(int size) {
    this.size = size;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawOval(0, 0, size, size);
  }
}