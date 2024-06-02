package src.views.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Field extends JPanel {

  private String message;
  private Image bgImageOn;
  private Image bgImageOff;
  private boolean isOn;

  public Field(String message, boolean isOn) {

    this.message = message;
    this.isOn = isOn;

    setLayout(new BorderLayout());

    // Add text only if the field is on
    JLabel text = new JLabel(message, JLabel.CENTER);
    text.setFont(new Font("Arial", Font.BOLD, 16));
    add(text, BorderLayout.CENTER);
    text.setVisible(isOn);

    // Load image
    try {
      bgImageOn = ImageIO.read(new File("assets/images/field-on.png"));
      bgImageOff = ImageIO.read(new File("assets/images/field-off.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setOn(boolean isOn) {
    this.isOn = isOn;

    // Show or hide text
    for (Component component : getComponents()) {
      component.setVisible(isOn);
    }

    repaint();
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
