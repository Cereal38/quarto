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
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class Pawn extends JButton {

  private boolean cursorSet = true;

  private boolean isPlayed = false;
  private boolean isSelected = false;

  public Pawn(String code, int width, int height, PawnsBar parent, int index) {
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
          if (GameStatusHandler.isSelectionPhase()) {
            setCursor(null);
            cursorSet = false; // Cursor set permanently to HAND_CURSOR
            select();
            System.err.println("Pawn clicked");
            parent.refresh();
          } else {
            System.err.println("Not selection phase - " + GameStatusHandler.getGamePhaseAsText());
          }
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

  }

  // getter and setter for played
  public boolean isPlayed() {
    return isPlayed;
  }

  public void setPlayed(boolean played) {
    this.isPlayed = played;
  }

  public void select() {
    isSelected = true;
    setBorder(BorderFactory.createLineBorder(Color.RED, 3));

    // Change the game phase to let the other player play the pawn
    GameStatusHandler.nextPhase();
  }

  public boolean isSelected() {
    return isSelected;
  }
}
