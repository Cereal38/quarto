package src.views.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.game.board.Board;
import src.views.game.board.PawnsBar;
import src.views.utils.ImageUtils;

public class Pawn extends JButton {

  private boolean cursorSet = true;

  private boolean played = false;

  public Pawn(String code, int width, int height, PawnsBar parent, int index) {
    // Load image
    ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);

    // Set image
    setIcon(image);
    setContentAreaFilled(false);
    setBorder(BorderFactory.createEmptyBorder());
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Set cursor to HAND_CURSOR on the first click
    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (cursorSet) {
          setCursor(null);
          cursorSet = false; // Cursor set permanently to HAND_CURSOR
          setPlayed(true);
          System.err.println("Pawn clicked");
          parent.selectPawn(index);
          parent.refresh();
        }
      }
    });
  }

  public Pawn(String code, int width, int height, Board parent) {
    // Load image
    ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);

    // Set image
    setIcon(image);
    setContentAreaFilled(false);
    setBorder(BorderFactory.createEmptyBorder());
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Set cursor to HAND_CURSOR on the first click
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        if (cursorSet) {
          setCursor(null);
          cursorSet = false; // Cursor set permanently to HAND_CURSOR
          setPlayed(true);
          System.err.println("Pawn clicked");
          parent.refresh();
        }
      }
    });
  }

  // getter and setter for played
  public boolean isPlayed() {
    return played;
  }

  public void setPlayed(boolean played) {
    this.played = played;
  }

  public void select() {
    setBorder(BorderFactory.createLineBorder(Color.RED, 3));
  }
}
