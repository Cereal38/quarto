/**
 * The QuartoBoard class represents the game board for a game of Quarto.
 * It manages the state of the game board, available pawns, selected pawn,
 * and provides methods for checking winning situations.
 */
package src.model.game;

import java.util.List;

public class QuartoBoard {
    /** The 2D array representing the game board, where each cell holds a QuartoPawn object. */
    private QuartoPawn[][] table;

    /** An array containing the available pawns that can be placed on the game board. */
    private QuartoPawn[] pawnAvailable;

    /** The currently selected pawn to be placed on the game board. */
    private QuartoPawn selectedPawn;

    /** The QuartoWin object responsible for managing win conditions on the game board. */
    private QuartoWin win;

    /**
     * Constructs a new QuartoBoard object.
     * Initializes the game board, available pawns, and sets up a new game.
     */
    public QuartoBoard() {
        newTable();
        win = new QuartoWin();
        pawnAvailable = new QuartoPawn[16];
        for (byte count = 0; count < 16; count++) {
            pawnAvailable[count] = new QuartoPawn(count);
        }
    }

    /**
     * Initializes the game board with a new empty table.
     */
    private void newTable() {
        table = new QuartoPawn[4][4]; // table filled with null
    }

    /**
     * Retrieves the currently selected pawn.
     *
     * @return The currently selected QuartoPawn, or null if no pawn is selected.
     */
    public QuartoPawn getSelectedPawn() {
        return selectedPawn;
    }

    /**
     * Sets the selected pawn on the board.
     *
     * @param pawn The QuartoPawn to set as selected.
     */
    public void setSelectedPawn(QuartoPawn pawn) {
        selectedPawn = pawn;
    }

    /**
     * Retrieves the pawn at the specified position on the game board.
     *
     * @param line   The row index.
     * @param column The column index.
     * @return The QuartoPawn at the specified position.
     * @throws IllegalArgumentException if the specified position is invalid.
     */
    public QuartoPawn getPawnAtPosition(int line, int column) {
        if (line < 0 || line >= 4 || column < 0 || column >= 4) {
            throw new IllegalArgumentException("Invalid position: (" + line + ", " + column + ")");
        }
        return table[line][column];
    }

    /**
     * Retrieves the current state of the game board.
     *
     * @return A 2D array of QuartoPawn objects representing the game board.
     */
    public QuartoPawn[][] getTable() {
        return table;
    }

    /**
     * Sets the pawn at the specified position on the game board.
     *
     * @param i     The row index.
     * @param j     The column index.
     * @param pawn  The QuartoPawn to set at the specified position.
     */
    public void setTable(int i, int j, QuartoPawn pawn) {
        table[i][j] = pawn;
    }

    /**
     * Checks if the specified position on the game board is empty.
     *
     * @param line   The row index.
     * @param column The column index.
     * @return True if the position is empty, false otherwise.
     */
    public boolean isTableEmpty(int line, int column) {
        return getPawnAtPosition(line, column) == null;
    }

    /**
     * Retrieves the array of available pawns.
     *
     * @return An array of QuartoPawn objects representing available pawns.
     */
    public QuartoPawn[] getPawnAvailable() {
        return pawnAvailable;
    }

    /**
     * Checks if the list of available pawns is empty.
     *
     * @return True if the list of available pawns is empty, false otherwise.
     */
    public boolean isPawnListEmpty() {
        for (QuartoPawn pawn : pawnAvailable) {
            if (pawn != null)
                return false;
        }
        return true;
    }

    /**
     * Checks if a winning situation occurs after placing a pawn at the specified position.
     *
     * @param line   The line index.
     * @param column The column index.
     * @return True if a winning situation occurs, false otherwise.
     */
    public boolean winSituation(int line, int column) {
        return win.winSituationLine(getTable(), line) ||
                win.winSituationColumn(getTable(), column) ||
                win.winSituationDiagonal(getTable(), line, column);
    }

    /**
     * Retrieves the list of win lines on the game board.
     * Each win line is represented by an array of integers containing the indices of the winning positions.
     *
     * @return A list of arrays representing the win lines.
     */
    public List<int[]> getWinLine() {
        return win.getWinLine();
    }

    /**
     * Retrieves the rows of the game board.
     *
     * @return A 2D array of QuartoPawn objects representing the rows of the game board.
     */
    public QuartoPawn[][] getLines() {
        QuartoPawn[][] lines = new QuartoPawn[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(table[i], 0, lines[i], 0, 4);
        }
        return lines;
    }

    /**
     * Retrieves the columns of the game board.
     *
     * @return A 2D array of QuartoPawn objects representing the columns of the game board.
     */
    public QuartoPawn[][] getColumns() {
        QuartoPawn[][] columns = new QuartoPawn[4][4];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                columns[j][i] = table[i][j];
            }
        }
        return columns;
    }

    /**
     * Retrieves the diagonals of the game board.
     *
     * @return A 2D array of QuartoPawn objects representing the diagonals of the game board.
     */
    public QuartoPawn[][] getDiagonals() {
        QuartoPawn[][] diagonals = new QuartoPawn[2][4];
        for (int i = 0; i < 4; i++) {
            diagonals[0][i] = table[i][i];
            diagonals[1][i] = table[i][3 - i];
        }
        return diagonals;
    }

    /**
     * Retrieves the QuartoWin object responsible for managing win conditions on the game board.
     *
     * @return The QuartoWin object.
     */
    public QuartoWin getWin() {
        return win;
    }
}
