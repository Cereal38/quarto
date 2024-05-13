package src.views.components;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class Pawn extends JButton {

  // Constants
  public static final int NOT_PLAYED = 0;
  public static final int SELECTED = 1;
  public static final int PLAYED = 2;

  private String code;
  private int state;
  private int width;
  private int height;

  public Pawn(String code, int state, int width, int height) {

    this.code = code;
    this.state = state;
    this.width = width;
    this.height = height;

    // Load image
    ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);

    // Set image
    setIcon(image);
    setContentAreaFilled(false);
    setBorder(BorderFactory.createEmptyBorder());
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        EventsHandler.getController().selectPawn(code);
      }

    });

  }

}
