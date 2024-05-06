package src.views.components;

import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.ImageUtils;

public class Pawn extends JButton {

  private boolean cursorSet = true;

  public Pawn(String code, int width, int height) {
    // Load image
    ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);

    // Set image
    setIcon(image);
    setContentAreaFilled(false);
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Set cursor to HAND_CURSOR on the first click
    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (cursorSet) {
          setCursor(null);
          cursorSet = false; // Cursor set permanently to HAND_CURSOR
        }
      }
    });
  }
}
