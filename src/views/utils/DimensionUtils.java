package src.views.utils;

import javax.swing.JFrame;

public class DimensionUtils {
  private static JFrame mainFrame;

  public static void setMainFrame(JFrame mainFrame) {
    DimensionUtils.mainFrame = mainFrame;
  }

  public static JFrame getMainFrame() {
    return mainFrame;
  }

  public static int getMainFrameWidth() {
    return mainFrame.getSize().width;
  }

  public static int getMainFrameHeight() {
    return mainFrame.getHeight();
  }

}
