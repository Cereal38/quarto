package src.views.game.board;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javafx.scene.paint.Color;
import src.views.utils.EventsHandler;

public class Board extends JPanel {

  private Cell[][] cells = new Cell[4][4];
  private Image bgImage;
  private Image backGroundImage;

  public Board(int width, int height, int cellSize) {

    setLayout(new GridLayout(4, 4));
    setPreferredSize(new Dimension(width, height));
    // Create a 20-pixel margin around the grid
    // TODO: Change that
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Get and add all cells
    cells = EventsHandler.getController().getTable();
    for (Cell[] line : cells) {
      for (Cell cell : line) {
        if (cell != null) {
          add(cell);
        }
      }
    }

    try {
      // TODO: Load board only once
      bgImage = ImageIO.read(new File("assets/images/board.png"));
    backGroundImage = new ImageIcon(getClass().getResource("/assets/images/bg-board.png")).getImage();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }

}
