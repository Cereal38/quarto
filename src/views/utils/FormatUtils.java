package src.views.utils;

import src.model.game.QuartoPawn;

/**
 * This class contains all the methods used to format the data.
 */
public class FormatUtils {

  /**
   * Converts a string in format "0110" to a byte.
   *
   * @param str the string to convert
   * @return the byte value
   */
  public static byte stringToByte(String str) {
    return (byte) Integer.parseInt(str, 2);
  }

  /**
   * Converts a byte to a string in format "0110".
   *
   * @param b the byte to convert
   * @return the string value
   */
  public static String byteToString(byte b) {
    return String.format("%4s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
  }

  /**
   * Converts a QuartoPawn to a string in format "0110".
   * 
   * @param pawn the QuartoPawn to convert
   * @return the string value
   */
  public static String pawnToString(QuartoPawn pawn) {
    return byteToString(pawn.getPawn());
  }

  /**
   * Converts a string in format "0110" to an index in the range 0-15.
   * 
   * @param str the string to convert
   * @return the index value
   */
  public static int stringToIndex(String str) {
    return Integer.parseInt(str, 2);
  }

  /**
   * Converts a byte to an index in the range 0-15.
   * 
   * @param b the byte to convert
   * @return the index value
   */
  public static int byteToIndex(byte b) {
    return b & 0xFF;
  }

  /**
   * Converts an index in the range 0-15 to a string in format "0110". It's used
   * for some purposes in the model.
   * 
   * @param index the index to convert
   * @return the string value
   */
  public static String indexToString(int index) {
    return String.format("%4s", Integer.toBinaryString(index)).replace(' ', '0');
  }

}
