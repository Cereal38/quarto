package src.model;

public class QuartoHistory {
    int state; // If we have to select a pawn for the other player state = 0, if we have to
                // play the pawn state = 1. state = 2 if it's the start of the game.
    int indexPawn;
    int line, column;
    int currentPlayer;
    String playerName;
    private QuartoHistory previous, next;

    // We saved the choice of pawn for the next player
    public QuartoHistory(int index, QuartoHistory p, String name, int player) {
        state = 0;
        indexPawn = index;
        playerName = name;
        currentPlayer = player;
        previous = p;
        next = null;
    }

    // We save the placement of the pawn we placed.
    public QuartoHistory(int newLine, int newColumn, QuartoHistory p, String name, int player) {
        state = 1;
        line = newLine;
        column = newColumn;
        playerName = name;
        currentPlayer = player;
        previous = p;
        next = null;
    }

    // New history.
    public QuartoHistory() {
        state = 2;
        previous = null;
        next = null;
    }

    public QuartoHistory getNext() {
        return next;
    }

    public void setNext(QuartoHistory n) {
        next = n;
    }

    public QuartoHistory getPrevious() {
        return previous;
    }

    public void setPrevious(QuartoHistory p) {
        previous = p;
    }

    public int getIndexPawn() {
        return indexPawn;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getState() {
        return state;
    }
    
    public String getName() {
        return playerName;
    }

    public int getNumberOfThePlayer() {
        return currentPlayer;
    }
}
