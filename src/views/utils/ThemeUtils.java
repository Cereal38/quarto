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


  /**
   * Adds a theme listener to the list of listeners.
   *
   * @param listener the theme listener to be added
   */
  public static void addThemeListener(ThemeListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes a theme listener from the list of listeners.
   *
   * @param listener the theme listener to be removed
   */
  public static void removeThemeListener(ThemeListener listener) {
    listeners.remove(listener);
  }

  /**
   * Sets the theme of the application.
   *
   * @param theme the theme to set
   */
  private static void setTheme(int theme) {
    ThemeUtils.theme = theme;
    for (ThemeListener listener : listeners) {
      listener.updatedTheme();
    }
  }

  /**
   * Toggles the theme between light and dark.
   */
  public static void toggleTheme() {
    int newTheme = theme == LIGHT ? DARK : LIGHT;
    setTheme(newTheme);
  }

  /**
   * Gets the current theme.
   *
   * @return the current theme
   */
  public static int getTheme() {
    return theme;
  }

  /**
   * Gets the name of the current theme.
   *
   * @return the name of the current theme
   */
  public static String getThemeName() {
    return theme == LIGHT ? "light" : "dark";
  }

}
