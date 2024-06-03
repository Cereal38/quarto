/**
 * The QuartoPawn class represents a pawn in the game of Quarto.
 * Each pawn is defined by a binary value indicating its attributes.
 */
package src.model.game;

public class QuartoPawn {

  /** The binary representation of the pawn's attributes. */
  private final byte pawnBinary;

  /**
   * Constructs a new QuartoPawn instance with the specified binary representation.
   *
   * @param pawn The binary representation of the pawn's attributes.
   */
  public QuartoPawn(byte pawn) {
    pawnBinary = pawn;
  }

  /**
   * Checks if the pawn is round.
   *
   * @return true if the pawn is round, false otherwise.
   */
  public boolean isRound() {
    return (getRound() != 0);
  }

  /**
   * Checks if the pawn is white.
   *
   * @return true if the pawn is white, false otherwise.
   */
  public boolean isWhite() {
    return (getWhite() != 0);
  }

  /**
   * Checks if the pawn is little.
   *
   * @return true if the pawn is little, false otherwise.
   */
  public boolean isLittle() {
    return (getLittle() != 0);
  }

  /**
   * Checks if the pawn is hollow.
   *
   * @return true if the pawn is hollow, false otherwise.
   */
  public boolean isHollow() {
    return (getHollow() != 0);
  }

  /**
   * Retrieves the binary value representing the round attribute of the pawn.
   *
   * @return The binary value representing the round attribute.
   */
  public int getRound() {
    byte round = 0b1000;
    return (pawnBinary & round);
  }

  /**
   * Retrieves the binary value representing the white attribute of the pawn.
   *
   * @return The binary value representing the white attribute.
   */
  public int getWhite() {
    byte white = 0b0100;
    return (pawnBinary & white);
  }

  /**
   * Retrieves the binary value representing the little attribute of the pawn.
   *
   * @return The binary value representing the little attribute.
   */
  public int getLittle() {
    byte little = 0b0010;
    return (pawnBinary & little);
  }

  /**
   * Retrieves the binary value representing the hollow attribute of the pawn.
   *
   * @return The binary value representing the hollow attribute.
   */
  public int getHollow() {
    byte hollow = 0b0001;
    return (pawnBinary & hollow);
  }

  /**
   * Retrieves the binary representation of the pawn's attributes.
   *
   * @return The binary representation of the pawn.
   */
  public byte getPawn() {
    return pawnBinary;
  }
}