package src.views.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageUtils {

  public static ImageIcon loadImage(String nom, int width, int height) {
    try {
      // print ls
      String path = "assets/images/" + nom;
      ImageIcon icon = new ImageIcon(ImageIO.read(new File(path)));
      Image newIcon = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
      icon = new ImageIcon(newIcon);
      return icon;
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error: " + e);
      return null;
    }
  }

  public static JButton createButtonFromImage(ImageIcon icon) {
    JButton button = new JButton(icon);
    button.setBorder(BorderFactory.createEmptyBorder());
    button.setContentAreaFilled(false);

    return button;
  }

  public static String getPawn(int code) {
    // turn code (0-15) to byte (0000-1111) smartly
    switch (code) {
    case 0:
      return "0000";
    case 1:
      return "0001";
    case 2:
      return "0010";
    case 3:
      return "0011";
    case 4:
      return "0100";
    case 5:
      return "0101";
    case 6:
      return "0110";
    case 7:
      return "0111";
    case 8:
      return "1000";
    case 9:
      return "1001";
    case 10:
      return "1010";
    case 11:
      return "1011";
    case 12:
      return "1100";
    case 13:
      return "1101";
    case 14:
      return "1110";
    case 15:
      return "1111";
    }
    return null;
  }
}
