package src;

/* TODO
 * 
 * 
 * redo
 * undo
 * save
 * charge
 */

public class QuartoModel {
    private QuartoPawn[][] table;
    private int player;//1 for Player 1 and 2 for Player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;

    public QuartoModel() {
        table = new QuartoPawn[4][4];//table filled with null
        player = 1;//starting player is player 1
        pawnAvailable = new QuartoPawn[16];
        int count = 0;
        for (int round = 0; round < 2; round++) {
            for (int white = 0; white < 2; white++) {
                for (int little = 0; little < 2; little++) {
                    for (int hollow = 0; hollow < 2; hollow++) {
                        pawnAvailable[count] = new QuartoPawn(round, white, little, hollow);
                        count++;
                    }
                }
            }
        }
    }

    public boolean isTableEmpty(int line, int column) {
        return table[line][column] == null;
    }

    public void selectPawn(int pawnRemoved) {
        selectedPawn = pawnAvailable[pawnRemoved];
         for (int count = pawnRemoved; count + 1 < pawnAvailable.length; count++) {
             pawnAvailable[count] = pawnAvailable[count + 1];
            }
            pawnAvailable[pawnAvailable.length-1] = null;
    }

    public boolean isPawnListEmpty() {
        return pawnAvailable.length == 0;
    }
    
    public void switchPlayer() {
        player = (player == 1) ? 2 : 1;
    }

    public void playShot(QuartoPawn pawn, int line, int column) {
        if (isTableEmpty(line, column)) {
            table[line][column] = pawn;
        }
    }

    public boolean winSituation(int line, int column) {
        return (winSituationLine(line) || winSituationColumn(column)
                || winSituationDiagonal(line, column));
    }

    private boolean winSituationLine(int line) {
        if (isTableEmpty(line, 0))
                return false;
        boolean round = table[line][0].isRound();
        boolean rTrue = true;
        boolean white = table[line][0].isWhite();
        boolean wTrue = true;
        boolean little = table[line][0].isLittle();
        boolean lTrue = true;
        boolean hollow = table[line][0].isWhite();
        boolean hTrue = true;
        for (int i = 1; i < 4; i++) {
            if (isTableEmpty(line, i))
                return false;
            if (rTrue && table[line][i].isRound() != round)
                rTrue = false;
            if (wTrue && table[line][i].isWhite() != white)
                wTrue = false;
            if (lTrue && table[line][i].isLittle() != little)
                lTrue = false;
            if (hTrue && table[line][i].isHollow() != hollow)
                hTrue = false;
        }
        return rTrue || wTrue || lTrue || hTrue;
    }
    
    private boolean winSituationColumn(int column) {
        if (isTableEmpty(0, column))
            return false;
        boolean round = table[0][column].isRound();
        boolean rTrue = true;
        boolean white = table[0][column].isWhite();
        boolean wTrue = true;
        boolean little = table[0][column].isLittle();
        boolean lTrue = true;
        boolean hollow = table[0][column].isHollow();
        boolean hTrue = true;
        for (int i = 1; i < 4; i++) {
            if (isTableEmpty(i, column))
                return false;
            if (rTrue && table[i][column].isRound() != round)
                rTrue = false;
            if (wTrue && table[i][column].isWhite() != white)
                wTrue = false;
            if (lTrue && table[i][column].isLittle() != little)
                lTrue = false;
            if (hTrue && table[i][column].isHollow() != hollow)
                hTrue = false;
        }
        return rTrue || wTrue || lTrue || hTrue;
    }
    
    private boolean winSituationDiagonal(int line, int column) {
        int addColumn;
        if (line == column) {
            addColumn = 1;
            column = 0;
        } else if ((line + column) == 3) {
            addColumn = -1;
            column = 3;
        } else {
            return false;
        }
        if (isTableEmpty(0, column))
                return false;
        boolean round = table[0][column].isRound();
        boolean rTrue = true;
        boolean white = table[0][column].isWhite();
        boolean wTrue = true;
        boolean little = table[0][column].isLittle();
        boolean lTrue = true;
        boolean hollow = table[0][column].isHollow();
        boolean hTrue = true;

        for (int i = 0; i < 4; i++) {
            if (isTableEmpty(i, column))
                return false;
            if (rTrue && table[i][column].isRound() != round)
                rTrue = false;
            if (wTrue && table[i][column].isWhite() != white)
                wTrue = false;
            if (lTrue && table[i][column].isLittle() != little)
                lTrue = false;
            if (hTrue && table[i][column].isHollow() != hollow)
                hTrue = false;
            column = column + addColumn;
        }
        return rTrue || wTrue || lTrue || hTrue;
    }

    public QuartoPawn getSelectedPawn() {
        return selectedPawn;
    }

    public QuartoPawn[][] getTable() {
        return table;
    }

    public int getCurrentPlayer() {
        return player;
    }
}