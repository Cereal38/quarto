package src.model;

public class QuartoWin {

    public boolean isTableEmpty(QuartoPawn[][] table, int line, int column) {
        return table[line][column] == null;
    }

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

    public boolean winSituationLine(QuartoPawn[][] table, int line) {
        return checkWin(table[line]);
    }

    public boolean winSituationColumn(QuartoPawn[][] table, int column) {
        QuartoPawn[] columnArray = new QuartoPawn[4];
        for (int i = 0; i < 4; i++) {
            columnArray[i] = table[i][column];
        }
        return checkWin(columnArray);
    }

    public boolean winSituationDiagonal(QuartoPawn[][] table, int line, int column) {
        QuartoPawn[] diagonalArray = new QuartoPawn[4];
        if (line == column) {
            for (int i = 0; i < 4; i++) {
                diagonalArray[i] = table[i][i];
            }
        } else if ((line + column) == 3) {
            for (int i = 0; i < 4; i++) {
                diagonalArray[i] = table[i][3 - i];
            }
        } else {
            return false;
        }
        return checkWin(diagonalArray);
    }
}
