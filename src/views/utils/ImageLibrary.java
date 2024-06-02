package src.views.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * This class contains all images used in the application. Get an image by
 * calling ImageLibrary.getImage("name");
 */
public class ImageLibrary {

  private static java.util.Map<String, Image> imageMap = new HashMap<>();

  // Load all images and store them in the imageMap
  public void loadImages() {
    loadImage("light", "main-menu.jpg");
    loadImage("dark", "main-menu.jpg");
  }

  private static void loadImage(String theme, String name) {
    try {
      Image img = ImageIO.read(new File("assets/images/" + theme + "/" + name));
      imageMap.put(theme + name, img);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Image getImage(String theme, String name) {
    return imageMap.get(theme + name);
  }
}
