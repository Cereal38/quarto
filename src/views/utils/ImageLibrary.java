package src.views.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    loadImage("0000.png");
    loadImage("0001.png");
    loadImage("0010.png");
    loadImage("0011.png");
    loadImage("0100.png");
    loadImage("0101.png");
    loadImage("0110.png");
    loadImage("0111.png");
    loadImage("1000.png");
    loadImage("1001.png");
    loadImage("1010.png");
    loadImage("1011.png");
    loadImage("1100.png");
    loadImage("1101.png");
    loadImage("1110.png");
    loadImage("1111.png");
    loadImage("pawns-bar-left-slot.png");
    loadImage("pawns-bar-left-slot-hovered.png");
    loadImage("pawns-bar-left-slot-selected.png");
    loadImage("pawns-bar-left-slot-hint.png");
    loadImage("pawns-bar-right-slot.png");
    loadImage("pawns-bar-right-slot-hovered.png");
    loadImage("pawns-bar-right-slot-selected.png");
    loadImage("pawns-bar-right-slot-hint.png");
    loadImage("gameboard-left-top-bar.png");
    loadImage("gameboard-right-top-bar.png");
    loadImage("gameboard-center-top-bar.png");
    loadImage("squared-background.png");
    loadImage("crown.png");
    loadImage("undo.png");
    loadImage("redo.png");
    loadImage("menu.png");
    loadImage("history.png");
    loadImage("resume.png");
    loadImage("pause.png");
    loadImage("flat.png");
    loadImage("exit.png");
    loadImage("book.png");
    loadImage("brush.png");
    loadImage("en.png");
    loadImage("fr.png");
    loadImage("back.png");
    loadImage("highlight.png");
    loadImage("hint-cell.png");
    loadImage("double-arrow.png");
    loadImage("hint.png");
    loadImage("music-on.png");
    loadImage("music-off.png");
  }

  /**
   * Loads an image for both light and dark themes and stores them in the image
   * map.
   *
   * @param name the name of the image file
   */
  private static void loadImage(String name) {
    try {
      InputStream inputStreamLight = ClassLoader.getSystemClassLoader().getResourceAsStream("assets/images/light/"+name);
        assert inputStreamLight != null;
        Image imgLight = ImageIO.read(inputStreamLight);
      InputStream inputStreamDark = ClassLoader.getSystemClassLoader().getResourceAsStream("assets/images/dark/" +name);
        assert inputStreamDark != null;
        Image imgDark = ImageIO.read(inputStreamDark);
      imageMap.put("light" + name, imgLight);
      imageMap.put("dark" + name, imgDark);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the image corresponding to the specified theme and name from the
   * image map.
   *
   * @param theme the theme of the image (e.g., "light" or "dark")
   * @param name  the name of the image file
   * @return the image corresponding to the theme and name
   */
  public static Image getImage(String theme, String name) {
    return imageMap.get(theme + name);
  }

}
