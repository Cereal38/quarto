package src.model;

import java.util.List;

public class QuartoBoard {
    private QuartoPawn[][] table;
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    private QuartoWin win;

    public QuartoBoard() {
        newTable();
        win = new QuartoWin();
        pawnAvailable = new QuartoPawn[16];
        for (byte count = 0; count < 16; count++) {
            pawnAvailable[count] = new QuartoPawn(count);
        }
    }

    private void newTable() {
        table = new QuartoPawn[4][4]; // table filled with null
    }

    public QuartoPawn getSelectedPawn() {
        return selectedPawn;
    }

    public void setSelectedPawn(QuartoPawn pawn) {
        selectedPawn = pawn;
    }

    public QuartoPawn getPawnAtPosition(int line, int column) {
        if (line < 0 || line >= 4 || column < 0 || column >= 4) {
            throw new IllegalArgumentException("Invalid position: (" + line + ", " + column + ")");
        }
        return table[line][column];
    }

    public QuartoPawn[][] getTable() {
        return table;
    }

    public void setTable(int i, int j, QuartoPawn pawn) {
        table[i][j] = pawn;
    }

    public boolean isTableEmpty(int line, int column) {
        return getPawnAtPosition(line, column) == null;
    }

    public QuartoPawn[] getPawnAvailable() {
        return pawnAvailable;
    }

    public boolean isPawnListEmpty() {
        for (QuartoPawn pawn : pawnAvailable) {
            if (pawn != null)
                return false;
        }
        return true;
    }

    public boolean winSituation(int line, int column) {
        return win.winSituationLine(getTable(), line) ||
                win.winSituationColumn(getTable(), column) ||
                win.winSituationDiagonal(getTable(), line, column);
    }

    public List<int[]> getWinLine() {
        return win.getWinLine();
    }

    public QuartoPawn[][] getLines() {
        QuartoPawn[][] lines = new QuartoPawn[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(table[i], 0, lines[i], 0, 4);
        }
        return lines;
    }

    public QuartoPawn[][] getColumns() {
        QuartoPawn[][] columns = new QuartoPawn[4][4];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                columns[j][i] = table[i][j];
            }
        }
        return columns;
    }

    public QuartoPawn[][] getDiagonals() {
        QuartoPawn[][] diagonals = new QuartoPawn[2][4];
        for (int i = 0; i < 4; i++) {
            diagonals[0][i] = table[i][i];
            diagonals[1][i] = table[i][3 - i];
        }
        return diagonals;
    }

    public QuartoWin getWin() {
        return win;
    }
}
