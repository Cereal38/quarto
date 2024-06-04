package src.views.components;

import java.awt.Image;
import src.views.utils.ImageLibrary;
import src.views.utils.ThemeUtils;

/**
 * A wrapper class for themed images.
 * <p>
 * This class provides functionality to retrieve themed images based on the current application theme.
 */
public class ImageThemed {

  private Image imageLight;
  private Image imageDark;

  /**
   * Constructs a new ImageThemed object with the specified image name.
   *
   * @param name the name of the image
   */
  public ImageThemed(String name) {

    // Get the image from the library
    this.imageLight = ImageLibrary.getImage("light", name);
    this.imageDark = ImageLibrary.getImage("dark", name);

  }

  /**
   * Retrieves the themed image based on the current application theme.
   *
   * @return the themed image
   */
  public Image getImage() {
    return ThemeUtils.getTheme() == ThemeUtils.LIGHT ? imageLight : imageDark;
  }

  /**
   * Sets the size of the image.
   *
   * @param width  the width of the image
   * @param height the height of the image
   */
  public void setSize(int width, int height) {
    if (imageLight == null || imageDark == null) {
      return;
    }
    imageLight = imageLight.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    imageDark = imageDark.getScaledInstance(width, height, Image.SCALE_SMOOTH);
  }

}
