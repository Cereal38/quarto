package src.views.components;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

public class Pawn extends JButton implements ThemeListener {

  // Constants
  public static final int NOT_PLAYED = 0;
  public static final int SELECTED = 1;
  public static final int PLAYED = 2;

  private String code;
  private int state;
  private int width;
  private int height;
  private boolean hovered;
  private ImageThemed image;
  private ImageIcon imageIcon;

  public Pawn(String code, int state, int width, int height) {

    ThemeUtils.addThemeListener(this);

    this.code = code;
    this.state = state;
    this.width = width;
    this.height = height;

    // Load image
    // ImageIcon image = ImageUtils.loadImage(code + ".png", width, height);
    image = new ImageThemed(code + ".png");
    image.setSize(width, height);
    imageIcon = new ImageIcon(image.getImage());

    // Set image
    setIcon(imageIcon);
    setContentAreaFilled(false);
    setBorder(BorderFactory.createEmptyBorder());
    // setCursor(new Cursor(Cursor.HAND_CURSOR));

    if (state == SELECTED) {
      setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 2));
    }

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        GameStatusHandler.selectPawn(code);
      }

      public void mouseEntered(MouseEvent evt) {
        hovered = true;
      }

      public void mouseExited(MouseEvent evt) {
        hovered = false;
      }

    });

  }

  /**
   * Checks if the pawn can be selected.
   * 
   * @return true if the pawn can be selected, false otherwise.
   */
  private boolean canSelect() {
    return EventsHandler.getController().isSelectionPhase() && !EventsHandler.getController().isCurrentPlayerAI();
  }

  public void update(int state, int width, int height) {

    this.state = state;

    // Do nothing if the width and height are the same
    if (this.width == width && this.height == height) {
      return;
    }

    // If the width and height has been reduced, simply scale down the image
    if (width < this.width && height < this.height) {
      this.width = width;
      this.height = height;
      ImageIcon imageIcon = (ImageIcon) getIcon();
      Image image = imageIcon.getImage();
      Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      ImageIcon scaledIcon = new ImageIcon(scaledImage);
      setIcon(scaledIcon);
    }

    // If the width or height has been increased, reload the image
    // This is necessary to avoid pixelation
    else {
      this.width = width;
      this.height = height;
      image = new ImageThemed(code + ".png");
      image.setSize(width, height);
      imageIcon = new ImageIcon(image.getImage());
      setIcon(imageIcon);
    }
  }

  public boolean isSelected() {
    return state == SELECTED;
  }

  public boolean isHovered() {
    return hovered;
  }

  public Image getImage() {
    return ((ImageIcon) getIcon()).getImage();
  }

  @Override
  public void updatedTheme() {
    // Reload image
    ImageIcon imageIcon = new ImageIcon(image.getImage());
    setIcon(imageIcon);
  }
}
