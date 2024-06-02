package src.views.main.menu;

import src.views.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
  private Image backgroundImage;

  public RightPanel() {
    backgroundImage = ImageUtils.loadImage("woodSlots.png", 50, 50).getImage();
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
  }
}
