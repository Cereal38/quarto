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

  // Constants
  public static final int NOT_PLAYED = 0;
  public static final int SELECTED = 1;
  public static final int PLAYED = 2;

  private boolean cursorSet = true;
  private int state;

  public Pawn(String code, int width, int height) {
    // Load image
    ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);

    // Set image
    setIcon(image);
    setContentAreaFilled(false);
    setBorder(BorderFactory.createEmptyBorder());
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    this.state = NOT_PLAYED;

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        // If not played, select the pawn
        if (isNotPlayed()) {
          select();
          GameStatusHandler.setSelectedPawn(code);
        }
      }

    });

  }

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
            GameStatusHandler.setSelectedPawn(code);
            System.err.println("Pawn clicked");
            parent.refresh();
          } else {
            System.err.println("Not a selection phase - " + GameStatusHandler.getGamePhaseAsText());
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

  public boolean isNotPlayed() {
    return state == NOT_PLAYED;
  }

  public boolean isPlayed() {
    return state == PLAYED;
  }

  public boolean isSelected() {
    return state == SELECTED;
  }

  public void reset() {
    state = NOT_PLAYED;
    setBorder(BorderFactory.createEmptyBorder());
  }

  public void play() {
    state = PLAYED;
    GameStatusHandler.nextPhase();
  }

  public void select() {
    state = SELECTED;
    setBorder(BorderFactory.createLineBorder(Color.RED, 3));

    // Change the game phase to let the other player play the pawn
    GameStatusHandler.nextPhase();
  }
}
