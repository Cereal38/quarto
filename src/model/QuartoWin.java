package src.model;

public class QuartoWin {

    public boolean isTableEmpty(QuartoPawn[][] table,int line, int column) {
        return table[line][column] == null;
    }

    public boolean winSituationLine(QuartoPawn[][] table, int line) {
        if (isTableEmpty(table, line, 0))
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
            if (isTableEmpty(table, line, i))
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
    
    public boolean winSituationColumn(QuartoPawn[][] table, int column) {
        if (isTableEmpty(table, 0, column))
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
            if (isTableEmpty(table, i, column))
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
    
    public boolean winSituationDiagonal(QuartoPawn[][] table, int line, int column) {
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
        if (isTableEmpty(table, 0, column))
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
            if (isTableEmpty(table, i, column))
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

}
