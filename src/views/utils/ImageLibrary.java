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
  public static void loadImages() {
    loadImage("main-menu.jpg");
    loadImage("quarto.png");
    loadImage("text-button.png");
    loadImage("text-button-hovered.png");
    loadImage("text-button-clicked.png");
    loadImage("bg-board.png");
    loadImage("board.png");
    loadImage("field-on.png");
    loadImage("field-off.png");
    loadImage("text-field.png");
  }

  private static void loadImage(String name) {
    try {
      Image imgLight = ImageIO.read(new File("assets/images/light/" + name));
      Image imgDark = ImageIO.read(new File("assets/images/dark/" + name));
      imageMap.put("light" + name, imgLight);
      imageMap.put("dark" + name, imgDark);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Image getImage(String theme, String name) {
    return imageMap.get(theme + name);
  }
}
