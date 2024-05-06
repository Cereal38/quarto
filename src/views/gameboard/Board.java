package src.views.gameboard;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Board extends JPanel {

  private static final float GAP_FACTOR = (float) 0.3;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Get the size of the panel
    float height = (float) getHeight();
    float width = (float) getWidth();

    // Compute the cell size and the gap between cells
    float cellSize = (height - height * GAP_FACTOR) / 4;
    float gap = (height - cellSize * 4) / 5;

    // Center the board
    float startX = (float) (width / 2 - cellSize * 2 - gap * 1.5);
    float startY = (float) gap;

    // Draw the board. A 4x4 grid of outlined circles
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        g.drawOval((int) (startX + i * (cellSize + gap)), (int) (startY + j * (cellSize + gap)), (int) cellSize,
            (int) cellSize);
      }
    }

  }
}
