package src.views.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
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
    button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    return button;
  }

  public static Image darkenImage(Image image) {
    // Create an RGBImageFilter that darkens the image by 50%
    RGBImageFilter rgbImageFilter = new RGBImageFilter() {
      @Override
      public int filterRGB(int x, int y, int rgb) {
        int a = rgb & 0xFF000000;
        int r = (rgb & 0x00FF0000) >> 16;
        int g = (rgb & 0x0000FF00) >> 8;
        int b = rgb & 0x000000FF;

        // Darken the color by 50%
        r = (int) (r * 0.5);
        g = (int) (g * 0.5);
        b = (int) (b * 0.5);

        return a | (r << 16) | (g << 8) | b;
      }
    };

    // Create a FilteredImageSource that applies the RGBImageFilter
    FilteredImageSource filteredImageSource = new FilteredImageSource(image.getSource(), rgbImageFilter);

    // Create a new Image from the FilteredImageSource using the default Toolkit
    // instance
    return Toolkit.getDefaultToolkit().createImage(filteredImageSource);
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
