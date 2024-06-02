package src.views.components;

import java.awt.Image;
import src.views.utils.ImageLibrary;
import src.views.utils.ThemeUtils;

public class ImageThemed {

  private String name;
  private Image imageLight;
  private Image imageDark;

  public ImageThemed(String name) {
    this.name = name;

    // Get the image from the library
    this.imageLight = ImageLibrary.getImage("light", name);
    this.imageDark = ImageLibrary.getImage("dark", name);

  }

  public Image getImage() {
    return ThemeUtils.getTheme() == ThemeUtils.LIGHT ? imageLight : imageDark;
  }

  public void setSize(int width, int height) {
    if (imageLight == null || imageDark == null) {
      return;
    }
    imageLight = imageLight.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    imageDark = imageDark.getScaledInstance(width, height, Image.SCALE_SMOOTH);
  }

}
