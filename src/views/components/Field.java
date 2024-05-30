package src.views.components;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Field extends JPanel {

  private Image bgImageOn;
  private Image bgImageOff;
  private boolean isOn;

  public Field(String message, boolean isOn) {

    this.isOn = isOn;

    setLayout(new BorderLayout());
    add(new JLabel(message, JLabel.CENTER), BorderLayout.CENTER);

    // Load image
    try {
      // TODO: Load board only once
      bgImageOn = ImageIO.read(new File("assets/images/field-on.png"));
      bgImageOff = ImageIO.read(new File("assets/images/field-off.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    if (isOn) {
      g.drawImage(bgImageOn, 0, 0, getWidth(), getHeight(), this);
    } else {
      g.drawImage(bgImageOff, 0, 0, getWidth(), getHeight(), this);
    }
  }
}
