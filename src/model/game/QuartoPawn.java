package src.model.game;

public class QuartoPawn {
  private final byte pawnBinary;

  public QuartoPawn(byte pawn) {
    pawnBinary = pawn;
  }

  public boolean isRound() {
    return (getRound() != 0);
  }

  public boolean isWhite() {
    return (getWhite() != 0);
  }

  public boolean isLittle() {
    return (getLittle() != 0);
  }

  public boolean isHollow() {
    return (getHollow() != 0);
  }

  public int getRound() {
    byte round = 0b1000;
    return (pawnBinary & round);
  }

  public int getWhite() {
    byte white = 0b0100;
    return (pawnBinary & white);
  }

  public int getLittle() {
    byte little = 0b0010;
    return (pawnBinary & little);
  }

  public int getHollow() {
    byte hollow = 0b0001;
    return (pawnBinary & hollow);
  }

  public byte getPawn() {
    return pawnBinary;
  }
}