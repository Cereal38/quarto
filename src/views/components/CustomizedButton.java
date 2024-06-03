/**
 * A button with a customized appearance.
 */
package src.views.components;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtils;

public class CustomizedButton extends JButton implements ThemeListener {

  TranslatedString translatedString;
  // private static ImageIcon buttonImage;
  // private static ImageIcon buttonImageHover;
  // private static ImageIcon buttonImageClicked;
  private ImageThemed buttonImage = new ImageThemed("text-button.png");
  private ImageThemed buttonImageHover = new ImageThemed("text-button-hovered.png");
  private ImageThemed buttonImageClicked = new ImageThemed("text-button-clicked.png");

  /**
   * Constructs a CustomizedButton with the specified key.
   *
   * @param key the translation key for the button text
   */
  public CustomizedButton(String key) {
    super();
    ThemeUtils.addThemeListener(this);

    translatedString = new TranslatedString(key, this);

    // Remove all unwanted button style
    setBorder(null);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setOpaque(false);

    // Add styles
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Increase buttons font size
    setFont(new Font(getFont().getFontName(), getFont().getStyle(), 16));
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

  /**
   * Sets the translation key for the button text.
   *
   * @param key the translation key for the button text
   */
  public void setKey(String key) {
    translatedString.setKey(key);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }

}