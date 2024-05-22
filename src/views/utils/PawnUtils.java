package src.views.utils;

import src.views.components.Pawn;

/**
 * This class contains all the pawns used in the game. The all purpose of this
 * class is to only load the pawns 1 time.
 */
public class PawnUtils {

  private static Pawn[] pawns = new Pawn[16];

  /**
   * Initializes all the pawns.
   */
  public static void initPawns() {
    for (int i = 0; i < 16; i++) {
      // pawns[i] = new Pawn(FormatUtils.indexToString(i), Pawn.NOT_PLAYED, 50, 50);
      pawns[i] = new Pawn("1101", Pawn.NOT_PLAYED, 50, 50);
    }
  }

  /**
   * Retrieves a pawn by its string representation.
   * 
   * @param pawn   the string representation of the pawn
   * @param width  the width of the pawn
   * @param height the height of the pawn
   * @return the pawn object
   */
  public static Pawn getPawn(String pawn, int state, int width, int height) {
    int index = FormatUtils.stringToIndex(pawn);
    pawns[index].update(state, width, height);
    return pawns[index];
  }

}
