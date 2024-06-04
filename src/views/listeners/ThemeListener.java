package src.views.listeners;

/**
 * An interface for objects that listen for changes in the application's theme and need to update accordingly.
 */
public interface ThemeListener {
  /**
   * Method to notify listeners when the theme is updated.
   */
  void updatedTheme();
}
