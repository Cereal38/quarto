package src.views.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import src.views.listeners.ThemeListener;

public class ImageThemed extends Image implements ThemeListener {

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
