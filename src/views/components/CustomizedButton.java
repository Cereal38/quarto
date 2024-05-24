package src.views.components;

import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
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

    // Draw the appropriate image and text
    if (getModel().isPressed()) {
      g.drawImage(buttonImageClicked.getImage(), 0, 0, getWidth(), getHeight(), this);
      g.drawString(text, x, y + 4);
    } else if (getModel().isRollover()) {
      g.drawImage(buttonImageHover.getImage(), 0, 0, getWidth(), getHeight(), this);
      g.drawString(text, x, y + 2);
    } else {
      g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), this);
      g.drawString(text, x, y);
    }
  }

  public void setKey(String key) {
    translatedString.setKey(key);
  }

}