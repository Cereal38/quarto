package src.model.game;

/**
 * The QuartoHistory class represents a historical record of moves in the Quarto game.
 * It keeps track of each move made during the game, including the selection of pawns
 * and their placement on the game board.
 */

public class QuartoHistory {
    /** The state of the move: 0 for selecting a pawn, 1 for placing a pawn, and 2 for the start of the game. */
    int state;

    /** The index of the selected pawn. */
    int indexPawn;

    /** The line on the game board where the pawn is placed. */
    int line;

    /** The column on the game board where the pawn is placed. */
    int column;

    /** The number of the current player. */
    int currentPlayer;

    /** The name of the player associated with the move. */
    String playerName;

    /** The previous move in the game history. */
    private QuartoHistory previous;

    /** The next move in the game history. */
    private QuartoHistory next;

    /**
     * Constructs a QuartoHistory object representing the selection of a pawn.
     *
     * @param index The index of the selected pawn.
     * @param p The previous move in the game history.
     * @param name The name of the player making the move.
     * @param player The number of the current player.
     */
    public QuartoHistory(int index, QuartoHistory p, String name, int player) {
        state = 0;
        indexPawn = index;
        playerName = name;
        currentPlayer = player;
        previous = p;
        next = null;
    }

    /**
     * Constructs a QuartoHistory object representing the placement of a pawn on the game board.
     *
     * @param newLine The line on the game board where the pawn is placed.
     * @param newColumn The column on the game board where the pawn is placed.
     * @param p The previous move in the game history.
     * @param name The name of the player making the move.
     * @param player The number of the current player.
     */
    public QuartoHistory(int newLine, int newColumn, QuartoHistory p, String name, int player) {
        state = 1;
        line = newLine;
        column = newColumn;
        playerName = name;
        currentPlayer = player;
        previous = p;
        next = null;
    }

    /**
     * Constructs a new QuartoHistory object representing the start of the game.
     */
    public QuartoHistory() {
        state = 2;
        previous = null;
        next = null;
    }

    /**
     * Gets the next move in the game history.
     *
     * @return The next move in the game history.
     */
    public QuartoHistory getNext() {
        return next;
    }

    /**
     * Sets the next move in the game history.
     *
     * @param n The next move in the game history.
     */
    public void setNext(QuartoHistory n) {
        next = n;
    }

    /**
     * Gets the previous move in the game history.
     *
     * @return The previous move in the game history.
     */
    public QuartoHistory getPrevious() {
        return previous;
    }

    /**
     * Sets the previous move in the game history.
     *
     * @param p The previous move in the game history.
     */
    public void setPrevious(QuartoHistory p) {
        previous = p;
    }

    /**
     * Gets the index of the selected pawn.
     *
     * @return The index of the selected pawn.
     */
    public int getIndexPawn() {
        return indexPawn;
    }

    /**
     * Gets the line on the game board where the pawn is placed.
     *
     * @return The line on the game board where the pawn is placed.
     */
    public int getLine() {
        return line;
    }

    /**
     * Gets the column on the game board where the pawn is placed.
     *
     * @return The column on the game board where the pawn is placed.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the state of the move.
     *
     * @return The state of the move.
     */
    public int getState() {
        return state;
    }

    /**
     * Gets the name of the player associated with the move.
     *
     * @return The name of the player associated with the move.
     */
    public String getName() {
        return playerName;
    }

    /**
     * Gets the number of the current player.
     *
     * @return The number of the current player.
     */
    public int getNumberOfThePlayer() {
        return currentPlayer;
    }

    /**
     * Returns the current player.
     *
     * @return the integer representing the current player.
     */
    public int currentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the name of the player.
     *
     * @return the string representing the player's name.
     */
    public String playerName() {
        return playerName;
    }

}
