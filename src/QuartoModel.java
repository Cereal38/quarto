package src;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* TODO
 * 
 * charge
 */

public class QuartoModel {
    private QuartoPawn[][] table;
    private int player;//1 for Player 1 and 2 for Player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    History save, head;
    
    private void newTable() {
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

    public QuartoModel() {
        newTable();
        head = new History();
        save = head;
    }

    public void saveFile(String fileName) throws IOException {
        try {
            // We create the variable to write in the filename
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            History head_cp = head;
            while (head_cp != null) { //while we go through all the history we have
                if (head_cp.equals(save)) {//If we are on the current move, we'll write a special caractere to know which move we'll return.
                    if (head_cp.state == 0) {// If we have placed a pawn
                        //we write all the information of the pawn
                        printWriter.print(head_cp.indexPawn + " *\n");
                    } else if (head_cp.state == 1) {//If we choosed a pawn to give to the next player
                        //we write the position of the pawn we placed.
                        printWriter.print(head_cp.line + " " + head_cp.column + " *\n");
                    }
                } else {
                    if (head_cp.state == 0) {
                        printWriter.print(+head_cp.indexPawn + "\n");
                    } else if (head_cp.state == 1) {
                        printWriter.print(head_cp.line + " " + head_cp.column + "\n");
                    }
                }
                head_cp = head_cp.next;
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("impossible de trouver le fichier " + fileName);
        }
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

    private boolean canRedo() {
        if (save.next != null)
            return true;
        return false;
    }

    public void redo() {
        if (canRedo()) {
            if (save.next.state == 0) {//choice of pawn
                selectedPawn = pawnAvailable[save.next.indexPawn];
                //fonction remove pawn from pawnAvailable
                removePawn(save.next.indexPawn);
            } else if (save.next.state == 1) { //choice of place
                table[save.next.line][save.next.column] = selectedPawn;
            }
            save = save.next;
        }
    }

    private boolean canUndo() {
        if (save.precedent != null)
            return true;
        return false;
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
        if (canUndo()) {
            if (save.precedent.state == 0) {//we remove a pawn we placed
                table[save.line][save.column] = null;
            } else if (save.precedent.state == 1) {//we add the pawn choosen to the list of pawn available.
                addPawnAvailable(save.indexPawn);
            }
            save = save.precedent;
        }
    }

    public boolean isTableEmpty(int line, int column) {
        return table[line][column] == null;
    }

    public void selectPawn(int pawnRemoved) {
        selectedPawn = pawnAvailable[pawnRemoved];
        //Add a new history because we choosed what pawn the next player will play.
        save.next = new History(pawnRemoved, save);
        save.next.precedent = save;
        save = save.next;
        removePawn(pawnRemoved);
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
            save.next = new History(line, column, save);
            save.next.precedent = save;
            save = save.next;
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