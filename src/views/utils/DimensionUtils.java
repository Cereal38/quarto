package src.views.utils;

import javax.swing.JFrame;

public class DimensionUtils {
  private static JFrame mainFrame;
  private static int heightPawnsBarGameBoard;
  private static int widthPawnsBarGameBoard;
  private static int heightHistory;
  private static int widthHistory;
  private static int boardCellSize;
  private static int barCellSize;

  public static void setMainFrame(JFrame mainFrame) {
    DimensionUtils.mainFrame = mainFrame;
  }

  public static int getMainFrameWidth() {
    return mainFrame.getSize().width;
  }

  public static int getMainFrameHeight() {
    return mainFrame.getHeight();
  }

  public static void setBoardPawnsBar(int width, int height) {
    heightPawnsBarGameBoard = height;
    widthPawnsBarGameBoard = width;
  }

  public static int getBoardHistoryHeight() {
    return heightHistory;
  }

  public static int getBoardPawnsBarHeight() {
    return heightPawnsBarGameBoard;
  }

  public static int getBoardPawnsBarWidth() {
    return widthPawnsBarGameBoard;
  }

  public static void setHistory(int width, int height) {
    heightHistory = height;
    widthHistory = width;
  }

  public static int getHistoryWidth() {
    return widthHistory;
  }

  public static int getHistoryHeight() {
    return heightHistory;
  }

  public static void setBoardCellSize(int size) {
    boardCellSize = size;
  }

  public static int getBoardCellSize() {
    return boardCellSize;
  }

  public static void setBarCellSize(int size) {
    barCellSize = size;
  }

  public static int getBarCellSize() {
    return barCellSize;
  }

}
