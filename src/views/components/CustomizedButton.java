package src.views.components;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A button with a skin.
 */
public class CustomizedButton extends JButton {

  TranslatedString translatedString;
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
    super();

    translatedString = new TranslatedString(key, this);

    // Remove all unwanted button style
    setBorder(null);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setOpaque(false);

    // Add styles
    setCursor(new Cursor(Cursor.HAND_CURSOR));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    String text = translatedString.getText();
    g.setColor(getForeground());
    g.setFont(getFont());
    FontMetrics fontMetrics = g.getFontMetrics();
    int x = (getWidth() - fontMetrics.stringWidth(text)) / 2;
    int y = (getHeight() - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

    // Create a Graphics2D object from the Graphics object
    Graphics2D g2d = (Graphics2D) g;

    // Draw the appropriate image and text
    if (getModel().isPressed()) {
      g2d.drawImage(buttonImageClicked.getImage(), 0, 0, getWidth(), getHeight(), this);
      g2d.drawString(text, x, y + 4);
    } else if (getModel().isRollover() && isEnabled()) {
      g2d.drawImage(buttonImageHover.getImage(), 0, 0, getWidth(), getHeight(), this);
      g2d.drawString(text, x, y + 2);
    } else {
      // Set the opacity of the Graphics2D object if the button is disabled
      if (!isEnabled()) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      }

      g2d.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);

      // Reset the opacity of the Graphics2D object
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

      g2d.drawString(text, x, y);
    }
  }

  public void setKey(String key) {
    translatedString.setKey(key);
  }

}