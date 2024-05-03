package src.model;

/*
 * TODO
 * tester l'historique
 * faire une méthode pour renvoyer la liste des booléens de la méthode winsituations.
 * get et set pour les variables.
 * crée class QuartoWin
 */

public class QuartoModel {
    private QuartoPawn[][] table;
    private int player;//1 for Player 1 and 2 for Player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    QuartoFile histo;
    QuartoWin win;
    
    private void newTable() {
        table = new QuartoPawn[4][4];//table filled with null
        player = 1;//starting player is player 1
        pawnAvailable = new QuartoPawn[16];
        for (int count = 0; count < 16; count++) {
                pawnAvailable[count] = new QuartoPawn(count);
        }
    }

    public QuartoModel() {
        newTable();
        histo = new QuartoFile();
        win = new QuartoWin();
    }

    public void redo() {
        if (histo.canRedo()) {
            if (histo.save.next.state == 0) {//choice of pawn
                selectedPawn = pawnAvailable[histo.save.next.indexPawn];
                pawnAvailable[histo.save.next.indexPawn] = null;
                //switchplayer
            } else if (histo.save.next.state == 1) { //choice of place
                table[histo.save.next.line][histo.save.next.column] = selectedPawn;
            }
            histo.save = histo.save.next;
        }
    }

    public void undo() {
        if (histo.canUndo()) {
            if (histo.save.precedent.state == 0) {//we remove a pawn we placed
                //get last pawn
                table[histo.save.line][histo.save.column] = null;
                //switchplayer
            } else if (histo.save.precedent.state == 1) {//we add the pawn choosen to the list of pawn available.
                pawnAvailable[histo.save.indexPawn] = getSelectedPawn();
                setSelectedPawn(null);
            }
            histo.save = histo.save.precedent;
        }
    }

    public void selectPawn(int pawnRemoved) {
        setSelectedPawn(pawnAvailable[pawnRemoved]);
        //Add a new history because we chose what pawn the next player will play.
        histo.save.next = new QuartoHistory(pawnRemoved, histo.save);
        histo.save.next.precedent = histo.save;
        histo.save = histo.save.next;

        pawnAvailable[pawnRemoved] = null;
    }

    public boolean isPawnListEmpty() {
        for (int i = 0; i < 16; i++) {
            if (pawnAvailable[i] != null)
                return false;
        }
        return true;
    }
    
    public void switchPlayer() {
        player = (player == 1) ? 2 : 1;
    }

    public void playShot(int line, int column) {
        if (win.isTableEmpty(table, line, column)) {
            table[line][column] = selectedPawn;
            winSituation(line, column);
            histo.save.next = new QuartoHistory(line, column, histo.save);
            histo.save.next.precedent = histo.save;
            histo.save = histo.save.next;
        }
    }

    public boolean winSituation(int line, int column) {

        return (win.winSituationLine(table, line) || win.winSituationColumn(table, column)
                || win.winSituationDiagonal(table, line, column));
    }
    
    public void chargeGame(String fileName) {
        histo.chargeFile(fileName);
        QuartoHistory copy = histo.head;
        while(!copy.equals(histo.save)) {
            if (copy.state == 0) {
                selectedPawn = pawnAvailable[copy.indexPawn];
                pawnAvailable[copy.indexPawn] = null;
            } else if (copy.state == 1) {
                table[copy.line][copy.column] = selectedPawn;
            }
            copy = copy.next;
        }
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

    public int getCurrentPlayer() {
        return player;
    }
}