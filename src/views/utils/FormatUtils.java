package src.views.utils;

import src.model.QuartoPawn;

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
   * Converts a string in format "0110" to an index in the range 0-15. It's used
   * for some purposes in the model.
   * 
   * @param str the string to convert
   * @return the index value
   */
  public static int stringToIndex(String str) {
    return Integer.parseInt(str, 2);
  }

}
