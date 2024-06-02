package src.views.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Field extends JPanel {

  private JLabel textLbl;
  private Image bgImageOn;
  private Image bgImageOff;
  private boolean isOn;

  public Field(String message, boolean isOn) {

    this.isOn = isOn;

    setLayout(new BorderLayout());

    // Add text only if the field is on
    textLbl = new JLabel(message, JLabel.CENTER);
    textLbl.setFont(new Font("Arial", Font.BOLD, 16));
    add(textLbl, BorderLayout.CENTER);
    textLbl.setVisible(isOn);

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
    textLbl.setVisible(isOn);

    repaint();
  }

  public void setText(String text) {
    textLbl.setText(text);
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
