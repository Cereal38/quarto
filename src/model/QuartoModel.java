package src.model;

public class QuartoModel {
    private QuartoPawn[][] table;
    private int player;//1 for Player 1 and 2 for Player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    QuartoFile histo;
    
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
    }

    private void removePawn(int pawnRemoved) {
        QuartoPawn[] newList = new QuartoPawn[pawnAvailable.length - 1];
        int j = 0;
        for(int i = 0; i < newList.length; i++){
            if(j == pawnRemoved){
                j++;
            }
            newList[i] = pawnAvailable[j];
            j++;
        }
        pawnAvailable = newList;
    }

    public void redo() {
        if (histo.canRedo()) {
            if (histo.save.next.state == 0) {//choice of pawn
                selectedPawn = pawnAvailable[histo.save.next.indexPawn];
                removePawn(histo.save.next.indexPawn);
                //switchplayer
            } else if (histo.save.next.state == 1) { //choice of place
                table[histo.save.next.line][histo.save.next.column] = selectedPawn;
            }
            histo.save = histo.save.next;
        }
    }

    //We add a pawn we removed from the game to can use it again.
    private void addPawnAvailable(int indexPawn) {
        //we grow the list of pawn available;
        QuartoPawn[] newPawnAvailable = new QuartoPawn[pawnAvailable.length + 1];
        int count_old = 0;
        for (int count_new = 0; count_new < pawnAvailable.length + 1; count_new++) {
            //if the index of the pawn we want to add is equal to the counter, then we add it a this index.
            if (count_new == indexPawn) {
                newPawnAvailable[count_new] = selectedPawn;
                count_new++;
            }
            newPawnAvailable[count_new] = pawnAvailable[count_old];
            count_old++;
        }
        pawnAvailable = newPawnAvailable;
    }

    public void undo() {
        if (histo.canUndo()) {
            if (histo.save.precedent.state == 0) {//we remove a pawn we placed
                table[histo.save.line][histo.save.column] = null;
                //switchplayer
            } else if (histo.save.precedent.state == 1) {//we add the pawn choosen to the list of pawn available.
                addPawnAvailable(histo.save.indexPawn);
            }
            histo.save = histo.save.precedent;
        }
    }

    public boolean isTableEmpty(int line, int column) {
        return table[line][column] == null;
    }

    public void selectPawn(int pawnRemoved) {
        selectedPawn = pawnAvailable[pawnRemoved];
        //Add a new history because we chose what pawn the next player will play.
        histo.save.next = new QuartoHistory(pawnRemoved, histo.save);
        histo.save.next.precedent = histo.save;
        histo.save = histo.save.next;
        removePawn(pawnRemoved);
    }

    public boolean isPawnListEmpty() {
        return pawnAvailable.length == 0;
    }
    
    public void switchPlayer() {
        player = (player == 1) ? 2 : 1;
    }

    public void playShot(int line, int column) {
        if (isTableEmpty(line, column)) {
            table[line][column] = selectedPawn;
            histo.save.next = new QuartoHistory(line, column, histo.save);
            histo.save.next.precedent = histo.save;
            histo.save = histo.save.next;
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
    
    public void chargeGame(String fileName) {
        histo.chargeFile(fileName);
        QuartoHistory copy = histo.head;
        while(!copy.equals(histo.save)) {
            if (copy.state == 0) {
                selectedPawn = pawnAvailable[copy.indexPawn];
                removePawn(copy.indexPawn);
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