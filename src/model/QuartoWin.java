package src.model;

public class QuartoWin {

    private boolean checkWin(QuartoPawn[] lineOrColumn) {
        if (lineOrColumn[0] == null)
            return false;

        boolean round = lineOrColumn[0].isRound();
        boolean white = lineOrColumn[0].isWhite();
        boolean little = lineOrColumn[0].isLittle();
        boolean hollow = lineOrColumn[0].isHollow();

        for (int i = 1; i < 4; i++) {
            if (lineOrColumn[i] == null)
                return false;

            if (!lineOrColumn[i].isRound() == round &&
                    !lineOrColumn[i].isWhite() == white &&
                    !lineOrColumn[i].isLittle() == little &&
                    !lineOrColumn[i].isHollow() == hollow) {
                return false;
            }
        }

        return true; // If all are the same or at least one attribute matches
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
        if (line == column || (line + column) == 3) {
            QuartoPawn[] diagonalArray = new QuartoPawn[4];
            for (int i = 0; i < 4; i++) {
                diagonalArray[i] = (line == column) ? table[i][i] : table[i][3 - i];
            }
            return checkWin(diagonalArray);
        }
        return false;
    }

}