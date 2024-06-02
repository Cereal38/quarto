package src.views.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import javax.imageio.ImageIO;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtil;

public class ImageThemed extends Image implements ThemeListener {

  private String name;
  private Image image;

  public ImageThemed(String name) {
    this.name = name;
    try {
      String theme = ThemeUtil.getThemeName();
      this.image = ImageIO.read(new File("assets/images/" + theme + "/" + name));
    } catch (Exception e) {
      e.printStackTrace();
    }
    updatedTheme();
    ThemeUtil.addLanguageChangeListener(this);
  }

  @Override
  public void updatedTheme() {
    // TODO Auto-generated method stub
  }

  @Override
  public int getWidth(ImageObserver observer) {
    // Your implementation here
    return 0;
  }

  @Override
  public int getHeight(ImageObserver observer) {
    // Your implementation here
    return 0;
  }

  @Override
  public Object getProperty(String name, ImageObserver observer) {
    // Your implementation here
    return null;
  }

  @Override
  public Graphics getGraphics() {
    // Your implementation here
    return null;
  }

  @Override
  public ImageProducer getSource() {
    // Your implementation here
    return null;
  }

}
