package src.views.components;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A button with a skin.
 */
public class CustomizedButton extends TranslatedButton {

  private static ImageIcon buttonImage;
  private static ImageIcon buttonImageHover;
  private static ImageIcon buttonImageClicked;

  // Load the button images only once
  static {
    try {
      Image img = ImageIO.read(new File("assets/images/text-button.png"));
      buttonImage = new ImageIcon(img);

      img = ImageIO.read(new File("assets/images/text-button-hovered.png"));
      buttonImageHover = new ImageIcon(img);

      img = ImageIO.read(new File("assets/images/text-button-clicked.png"));
      buttonImageClicked = new ImageIcon(img);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public CustomizedButton(String key) {
    super(key);

    // Remove all unwanted button style
    setBorder(null);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the appropriate image
    if (getModel().isPressed()) {
      System.out.println("pressed");
      g.drawImage(buttonImageClicked.getImage(), 0, 0, getWidth(), getHeight(), this);
    } else if (getModel().isRollover()) {
      g.drawImage(buttonImageHover.getImage(), 0, 0, getWidth(), getHeight(), this);
    } else {
      g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
  }
}