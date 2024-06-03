package src.views.utils;

import javax.swing.JFrame;

/**
 * The DimensionUtils class provides utility methods for managing dimensions and sizes in the application.
 */

public class DimensionUtils {
  private static JFrame mainFrame;
  private static int heightPawnsBarGameBoard;
  private static int widthPawnsBarGameBoard;
  private static int heightHistory;
  private static int widthHistory;
  private static int boardCellSize;
  private static int barCellSize;

  /**
   * Sets the main frame of the application.
   *
   * @param mainFrame The main JFrame of the application.
   */
  public static void setMainFrame(JFrame mainFrame) {
    DimensionUtils.mainFrame = mainFrame;
  }

  /**
   * Gets the width of the main frame.
   *
   * @return The width of the main frame.
   */
  public static int getMainFrameWidth() {
    if (mainFrame == null) {
      return 0;
    }
    return mainFrame.getWidth();
  }

  /**
   * Gets the height of the main frame.
   *
   * @return The height of the main frame.
   */
  public static int getMainFrameHeight() {
    if (mainFrame == null) {
      return 0;
    }
    return mainFrame.getHeight();
  }

  /**
   * Sets the dimensions of the board pawns bar.
   *
   * @param width  The width of the pawns bar.
   * @param height The height of the pawns bar.
   */
  public static void setBoardPawnsBar(int width, int height) {
    heightPawnsBarGameBoard = height;
    widthPawnsBarGameBoard = width;
  }

  /**
   * Gets the height of the board history.
   *
   * @return The height of the board history.
   */
  public static int getBoardHistoryHeight() {
    return heightHistory;
  }

  /**
   * Gets the height of the board pawns bar.
   *
   * @return The height of the board pawns bar.
   */
  public static int getBoardPawnsBarHeight() {
    return heightPawnsBarGameBoard;
  }

  /**
   * Gets the width of the board pawns bar.
   *
   * @return The width of the board pawns bar.
   */
  public static int getBoardPawnsBarWidth() {
    return widthPawnsBarGameBoard;
  }

  /**
   * Sets the dimensions of the history panel.
   *
   * @param width  The width of the history panel.
   * @param height The height of the history panel.
   */
  public static void setHistory(int width, int height) {
    heightHistory = height;
    widthHistory = width;
  }

  /**
   * Gets the width of the history panel.
   *
   * @return The width of the history panel.
   */
  public static int getHistoryWidth() {
    return widthHistory;
  }

  /**
   * Gets the height of the history panel.
   *
   * @return The height of the history panel.
   */
  public static int getHistoryHeight() {
    return heightHistory;
  }

  /**
   * Sets the size of the board cells.
   *
   * @param size The size of the board cells.
   */
  public static void setBoardCellSize(int size) {
    boardCellSize = size;
  }

  /**
   * Gets the size of the board cells.
   *
   * @return The size of the board cells.
   */
  public static int getBoardCellSize() {
    return boardCellSize;
  }

  /**
   * Sets the size of the bar cells.
   *
   * @param size The size of the bar cells.
   */
  public static void setBarCellSize(int size) {
    barCellSize = size;
  }

  /**
   * Gets the size of the bar cells.
   *
   * @return The size of the bar cells.
   */
  public static int getBarCellSize() {
    return barCellSize;
  }
}
