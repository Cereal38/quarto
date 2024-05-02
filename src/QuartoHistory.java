package src;

public class QuartoHistory {
    int state; // If we have to select a pawn for the other player state = 0, if we have to play the pawn state = 1. state = 2 if it's the start of the game.
    int indexPawn;
    int line, column;
    QuartoHistory precedent, next;

    //We saved the choice of pawn for the next player
    public QuartoHistory(int index, QuartoHistory p) {
        state = 0;
        indexPawn = index;
        precedent = p;
        next = null;
    }

    //We save the placement of the pawn we placed.
    public QuartoHistory(int newLine, int newColumn, QuartoHistory p) {
        state = 1;
        line = newLine;
        column = newColumn;
        precedent = p;
        next = null;
    }

    //New history.
    public QuartoHistory() {
        state = 2;
        precedent = null;
        next = null;
    }
}
