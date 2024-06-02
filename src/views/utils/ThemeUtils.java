package src.views.utils;

import java.util.ArrayList;
import java.util.List;
import src.views.listeners.ThemeListener;

/**
 * This class allow to change the theme of the application.
 */
public class ThemeUtils {

  public static final int LIGHT = 0;
  public static final int DARK = 1;
  private static int theme = LIGHT;

  /**
   * The list of theme listeners.
   */
  private static final List<ThemeListener> listeners = new ArrayList<>();

  public static void addLanguageChangeListener(ThemeListener listener) {
    listeners.add(listener);
  }

  public static void removeLanguageChangeListener(ThemeListener listener) {
    listeners.remove(listener);
  }

  private static void setTheme(int theme) {
    ThemeUtils.theme = theme;
    for (ThemeListener listener : listeners) {
      listener.updatedTheme();
    }
  }

  public static void toggleTheme() {
    int newTheme = theme == LIGHT ? DARK : LIGHT;
    setTheme(newTheme);
  }

  public static int getTheme() {
    return theme;
  }

  public static String getThemeName() {
    return theme == LIGHT ? "light" : "dark";
  }

}
