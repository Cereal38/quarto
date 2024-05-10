package src.views.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class Pawn extends JButton {

  // Constants
  public static final int NOT_PLAYED = 0;
  public static final int SELECTED = 1;
  public static final int PLAYED = 2;

  private String code;
  private int state;

  public Pawn(String code, int width, int height) {

    this.state = NOT_PLAYED;
    this.code = code;

    // Load image
    ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);

    // Set image
    setIcon(image);
    setContentAreaFilled(false);
    setBorder(BorderFactory.createEmptyBorder());
    setCursor(new Cursor(Cursor.HAND_CURSOR));

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

  public boolean isNotPlayed() {
    return state == NOT_PLAYED;
  }

  public boolean isPlayed() {
    return state == PLAYED;
  }

  public boolean isSelected() {
    return state == SELECTED;
  }

  /**
   * Resets the state of the pawn to its initial state.
   */
  public void reset() {
    state = NOT_PLAYED;
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setBorder(BorderFactory.createEmptyBorder());
  }

  /**
   * Update the state of the pawn to PLAYED and change the game phase.
   */
  public void play() {
    state = PLAYED;
    setBorder(BorderFactory.createEmptyBorder());
    GameStatusHandler.nextPhase();
  }

  /**
   * Update the state of the pawn to SELECTED and change the game phase.
   */
  public void select() {
    state = SELECTED;
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    GameStatusHandler.nextPhase();
  }

  public String getCode() {
    return code;
  }
}
