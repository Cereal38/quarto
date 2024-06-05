package src.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The QuartoWin class represents the winning conditions and lines in a game of Quarto.
 * It provides methods for checking winning situations and retrieving win lines.
 */

public class QuartoWin {
  /** List to store winning line*/
  private List<int[]> winLine; //

  /**
   * Constructs a new QuartoWin object.
   * Initializes the list of winning lines.
   */
  public QuartoWin() {
    this.winLine = new ArrayList<>();
  }

  /**
   * Retrieves the list of the winning line.
   *
   * @return The list of the winning line.
   */
  public List<int[]> getWinLine() {
    return winLine;
  }

  /**
   * Checks if a winning situation occurs in a specific line.
   *
   * @param table The game board represented as a 2D array of QuartoPawn objects.
   * @param line  The index of the line to check.
   * @return True if a winning situation occurs in the line, false otherwise.
   */
  public boolean winSituationLine(QuartoPawn[][] table, int line) {
    boolean win = checkWin(table[line]);
    if (win) {
      int[] axis;
      clearWinLine();
      for (int column = 0; column < 4; column++) {
        axis = new int[2];
        axis[0] = line;
        axis[1] = column;
        winLine.add(axis);
      }
    }
    return win;
  }

  /**
   * Checks if a winning situation occurs in a specific column.
   *
   * @param table  The game board represented as a 2D array of QuartoPawn objects.
   * @param column The index of the column to check.
   * @return True if a winning situation occurs in the column, false otherwise.
   */
  public boolean winSituationColumn(QuartoPawn[][] table, int column) {
    QuartoPawn[] columnArray = new QuartoPawn[4];
    for (int i = 0; i < 4; i++) {
      columnArray[i] = table[i][column];
    }
    boolean win = checkWin(columnArray);
    if (win) {
      int[] axis;
      clearWinLine();
      for (int line = 0; line < 4; line++) {
        axis = new int[2];
        axis[0] = line;
        axis[1] = column;
        winLine.add(axis);
      }
    }
    return win;
  }

  /**
   * Checks if a winning situation occurs in any of the diagonals.
   *
   * @param table  The game board represented as a 2D array of QuartoPawn objects.
   * @param line   The index of the line of the recently placed pawn.
   * @param column The index of the column of the recently placed pawn.
   * @return True if a winning situation occurs in any of the diagonals, false otherwise.
   */
  public boolean winSituationDiagonal(QuartoPawn[][] table, int line, int column) {
    if (line == column || (line + column) == 3) {
      QuartoPawn[] diagonalArray = new QuartoPawn[4];
      for (int i = 0; i < 4; i++) {
        diagonalArray[i] = (line == column) ? table[i][i] : table[i][3 - i];
      }
      boolean win = checkWin(diagonalArray);
      if (win) {
        int[] axis;
        clearWinLine();
        for (int i = 0; i < 4; i++) {
          axis = new int[2];
          axis[0] = i;
          axis[1] = (line == column) ? i : 3 - i;
          winLine.add(axis);
        }
      }
      return win;
    }
    return false;
  }

  /**
   * Checks if a winning situation occurs in a specific line, column, or diagonal.
   *
   * @param lineOrColumn An array representing a line, column, or diagonal of the game board.
   * @return True if a winning situation occurs, false otherwise.
   */
  private boolean checkWin(QuartoPawn[] lineOrColumn) {
    if (lineOrColumn[0] == null)
      return false;
    boolean round = lineOrColumn[0].isRound();
    boolean rTrue = true;
    boolean white = lineOrColumn[0].isWhite();
    boolean wTrue = true;
    boolean little = lineOrColumn[0].isLittle();
    boolean lTrue = true;
    boolean hollow = lineOrColumn[0].isHollow();
    boolean hTrue = true;
    for (int i = 1; i < 4; i++) {
      if (lineOrColumn[i] == null)
        return false;
      if (rTrue && lineOrColumn[i].isRound() != round)
        rTrue = false;
      if (wTrue && lineOrColumn[i].isWhite() != white)
        wTrue = false;
      if (lTrue && lineOrColumn[i].isLittle() != little)
        lTrue = false;
      if (hTrue && lineOrColumn[i].isHollow() != hollow)
        hTrue = false;
    }
    return rTrue || wTrue || lTrue || hTrue;
  }

  /**
   * Clears the list of winning lines.
   */
  public void clearWinLine() {
    winLine.clear();
  }
}
