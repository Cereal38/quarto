package src.views.utils;

public class FormatUtils {

  /**
   * Converts a string in format "01" to a byte.
   *
   * @param str the string to convert
   * @return the byte value
   */
  public static byte stringToByte(String str) {
    return (byte) Integer.parseInt(str, 2);
  }

  /**
   * Converts a byte to a string in format "01".
   *
   * @param b the byte to convert
   * @return the string value
   */
  public static String byteToString(byte b) {
    return String.format("%4s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
  }

}
