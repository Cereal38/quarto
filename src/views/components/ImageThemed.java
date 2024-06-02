package src.views.components;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import src.views.listeners.ThemeListener;
import src.views.utils.ImageLibrary;
import src.views.utils.ThemeUtils;

public class ImageThemed implements ThemeListener {

  private String name;
  private Image imageLight;
  private Image imageDark;
  private static final List<ThemeListener> listeners = new ArrayList<>();

  public ImageThemed(String name) {
    this.name = name;

    // Get the image from the library
    this.imageLight = ImageLibrary.getImage("light", name);
    this.imageDark = ImageLibrary.getImage("dark", name);

    ThemeUtils.addLanguageChangeListener(this);
    listeners.add(this);
    updatedTheme();
  }

  @Override
  public void updatedTheme() {
    // Call repaint() on all registered ThemeListener components
    for (ThemeListener listener : listeners) {
      if (listener instanceof JComponent) {
        ((JComponent) listener).repaint();
      }
    }
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
