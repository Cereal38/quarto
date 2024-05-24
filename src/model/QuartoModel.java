package src.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuartoModel {
    private QuartoPawn[][] table;
    private int currentPlayer;// 1 for Player 1 and 2 for Player 2
    private int[] playerType = new int[2]; // 0 for Human, 1 for Random AI, 2 for Easy AI and 3 for the MiniMax AI
    // with playerType[0] type of the player 1 and playerType[1] type of the player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    private QuartoFile file;
    private QuartoWin win;
    private SlotManager manager;
    private String firstPlayerName, secondPlayerName;
    private Player randomAIPlayer, easyAIPlayer, minimaxAIPlayer;
    private boolean gameOver = false; // true if end game

    public QuartoModel(int index) {
        newTable(0, 0);
        file = new QuartoFile();
        win = new QuartoWin();
        manager = new SlotManager();
        chargeGame(index);
        if (playerType[0] == 1 || playerType[1] == 1) {
            randomAIPlayer = new RandomAIPlayer();
        }
        if (playerType[0] == 2 || playerType[1] == 2) {
            easyAIPlayer = new EasyAIPlayer();
        }
        if(playerType[0] == 3 || playerType[1] == 3) {
            minimaxAIPlayer = new MiniMaxAIPlayer(15);
        }
    }

    public QuartoModel(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName) {
        newTable(firstPlayerType, secondPlayerType);
        file = new QuartoFile();
        win = new QuartoWin();
        manager = new SlotManager();
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        if (playerType[0] == 1 || playerType[1] == 1) {
            randomAIPlayer = new RandomAIPlayer();
        }
        if (playerType[0] == 2 || playerType[1] == 2) {
            easyAIPlayer = new EasyAIPlayer();
        }
        if(playerType[0] == 3 || playerType[1] == 3) {
            minimaxAIPlayer = new MiniMaxAIPlayer(4);
        }
    }

    private void newTable(int firstPlayerType, int secondPlayerType) {
        table = new QuartoPawn[4][4];// table filled with null
        currentPlayer = 1;// starting player is player 1
        playerType[0] = firstPlayerType;
        playerType[1] = secondPlayerType;
        pawnAvailable = new QuartoPawn[16];
        for (byte count = 0; count < 16; count++) {
            pawnAvailable[count] = new QuartoPawn(count);
        }
    }

    public void redo() {
        if (file.canRedo()) {
            if (file.getNextState() == 0) {// choice of pawn
                setSelectedPawn(pawnAvailable[file.getNextIndexPawn()]);
                pawnAvailable[file.getNextIndexPawn()] = null;
                switchPlayer();// next player
            } else if (file.getNextState() == 1) { // choice of place
                setTable(file.getNextLine(), file.getNextColumn(), getSelectedPawn());
                setSelectedPawn(null);
            }
            file.setSave(file.getSave().getNext());
        }
    }

    public void undo() {
        if (file.canUndo()) {
            if (file.getPreviousState() == 0) {// we remove a placed pawn
                setSelectedPawn(getPawnAtPosition(file.getLine(), file.getColumn()));
                setTable(file.getLine(), file.getColumn(), null);
                if (gameOver)
                    gameOver = false;
            } else if (file.getPreviousState() == 1) {// we add the pawn chosen to the list of pawn available.
                pawnAvailable[file.getIndexPawn()] = getSelectedPawn();
                setSelectedPawn(null);
                switchPlayer();// next player
            } else { // before the first move
                pawnAvailable[file.getIndexPawn()] = getSelectedPawn();
                setSelectedPawn(null);
            }
            file.setSave(file.getSave().getPrevious());
        }
    }

    public void selectPawn(int indexPawn) {
        if (getCurrentPlayerType() == 1) {
            randomAIPlayer.selectPawn(this);
        }
        else if (getCurrentPlayerType() == 2){
            easyAIPlayer.selectPawn(this);
        }
        else if (getCurrentPlayerType() == 3){
            minimaxAIPlayer.selectPawn(this);
        }
        else {
            if (pawnAvailable[indexPawn] != null) {
                selectPawnHuman(indexPawn);
            }
        }
    }

    public void selectPawnHuman(int indexPawn) {
        setSelectedPawn(pawnAvailable[indexPawn]);
        // Add a new history because we chose what pawn the next player will play.
        file.getSave().setNext(new QuartoHistory(indexPawn, file.getSave(), getNameOfTheCurrentPlayer(), currentPlayer));
        file.getSave().getNext().setPrevious(file.getSave());
        file.setSave(file.getSave().getNext());
        pawnAvailable[indexPawn] = null;
        switchPlayer();// next player
    }

    public boolean isPawnListEmpty() {
        for (int i = 0; i < 16; i++) {
            if (pawnAvailable[i] != null)
                return false;
        }
        return true;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public void playShot(int line, int column) {
        if (getCurrentPlayerType() == 1) {
            randomAIPlayer.playShot(this);
        }
        else if(getCurrentPlayerType() == 2){
            easyAIPlayer.playShot(this);
        }
        else if(getCurrentPlayerType() == 3){
            minimaxAIPlayer.playShot(this);
        }
        else {
            playShotHuman(line, column);
        }
    }

    public void playShotHuman(int line, int column) {
        if (isTableEmpty(line, column)) {
            setTable(line, column, selectedPawn);
            winSituation(line, column);
            setSelectedPawn(null);
            file.getSave().setNext(new QuartoHistory(line, column, file.getSave(), getNameOfTheCurrentPlayer(), currentPlayer));
            file.getSave().getNext().setPrevious(file.getSave());
            file.setSave(file.getSave().getNext());
        }
    }

    public boolean winSituation(int line, int column) {
        gameOver = win.winSituationLine(getTable(), line) || win.winSituationColumn(getTable(), column) || win.winSituationDiagonal(getTable(), line, column);
        return (gameOver);
    }

    public void redoLoop(int countUndo) {
        while (countUndo != 0) {
            redo();
            countUndo--;
        }
    }

    public boolean checkInfoPlayer(int index) {
        int countUndo = 0;
        manager.loadFromDirectory();
        while (file.canUndo()) {
            undo();
            countUndo++;
        }
        System.out.println("nb files =" + manager.getSlotFileDates().size());
        if (manager.isSlotFileEmpty(index)) {
            System.err.println("l'index: " + index + " contient " + manager.getSlotFileDates().keySet().toArray(new String[0])[index] + " un fichier vide");
            redoLoop(countUndo);
            return false;
        }
        String[] infoPlayer = file.chargeFile(manager.getSlotFileDates().keySet().toArray(new String[0])[index]);
        if (infoPlayer == null) {
            redoLoop(countUndo);
            return false;
        }
        String[] nameAndType = infoPlayer[0].split(" ");
        if (nameAndType.length != 2) {
            System.err.println("il manque une information pour le joueur 1");
            redoLoop(countUndo);
            return false;
        }
        firstPlayerName = nameAndType[0];
        playerType[0] = Integer.parseInt(nameAndType[1]);
        nameAndType = infoPlayer[1].split(" ");
        if (nameAndType.length != 2) {
            System.err.println("il manque une information pour le joueur 2");
            redoLoop(countUndo);
            return false;
        }
        secondPlayerName = nameAndType[0];
        playerType[1] = Integer.parseInt(nameAndType[1]);
        return true;
    }

    public void chargeGame(int index) {
        if (!checkInfoPlayer(index))
            return;
        QuartoHistory copy = file.getHead();
        boolean afterSave = false;
        while (!afterSave) {
            if (copy.state == 0) {
                selectedPawn = pawnAvailable[copy.getIndexPawn()];
                pawnAvailable[copy.getIndexPawn()] = null;
            } else if (copy.state == 1) {
                setTable(copy.getLine(), copy.getColumn(), getSelectedPawn());
            }
            if (copy.equals(file.getSave())) {
                afterSave = true;
            }
            copy = copy.getNext();
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

    public void saveFile(int index) throws IOException {
        manager.loadFromDirectory();
        manager.renameSlotFile(index, firstPlayerName, secondPlayerName);
        String fileName = firstPlayerName + "_vs_" + secondPlayerName + ".txt";
        String filePath = "slots" + File.separator + fileName;
        file.saveFile(filePath, firstPlayerName, secondPlayerName, playerType);
    }

    public QuartoPawn[][] getTable() {
        return table;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public QuartoFile getFile() {
        return file;
    }

    private void setTable(int i, int j, QuartoPawn pawn) {
        table[i][j] = pawn;
    }

    public boolean isTableEmpty(int line, int column) {
        return getPawnAtPosition(line, column) == null;
    }

    public QuartoPawn[] getPawnAvailable() {
        return pawnAvailable;
    }

    public int getCurrentPlayerType() {
        return playerType[getCurrentPlayer() - 1];
    }

    public void setPlayerType(int[] playerType) {
        if (playerType.length != 2) {
            throw new IllegalArgumentException("playerType array must have length 2");
        }
        this.playerType = playerType;
    }

    public String getNameOfTheCurrentPlayer() {
        return (currentPlayer == 1) ? firstPlayerName : secondPlayerName;
    }

    public int stateOfGame() {
        return (file.getState() == 0) ? 1 : 0;
    }

    public QuartoHistory getCurrentState() {
        return file.getSave();
    }

    public QuartoHistory getFirstState() {
        return file.getHead();
    }

    public boolean isGameOver() {
        return gameOver || (selectedPawn == null && isPawnListEmpty());
    }

    public QuartoPawn getPawn(int pawnIndex){
        return getPawnAvailable()[pawnIndex];
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

        // First diagonal (top-left to bottom-right)
        for (int i = 0; i < 4; i++) {
            diagonals[0][i] = table[i][i];
        }

        // Second diagonal (top-right to bottom-left)
        for (int i = 0; i < 4; i++) {
            diagonals[1][i] = table[i][3 - i];
        }

        return diagonals;
    }

    public List<QuartoPawn[]> getIntersectingLines(int row, int column) {
        List<QuartoPawn[]> intersectingLines = new ArrayList<>();

        // Add the row
        QuartoPawn[] lineRow = new QuartoPawn[4];
        System.arraycopy(table[row], 0, lineRow, 0, 4);
        intersectingLines.add(lineRow);

        // Add the column
        QuartoPawn[] lineColumn = new QuartoPawn[4];
        for (int i = 0; i < 4; i++) {
            lineColumn[i] = table[i][column];
        }
        intersectingLines.add(lineColumn);

        // Add the diagonals if the position is on a diagonal
        if (row == column) {
            QuartoPawn[] diagonal1 = new QuartoPawn[4];
            for (int i = 0; i < 4; i++) {
                diagonal1[i] = table[i][i];
            }
            intersectingLines.add(diagonal1);
        }

        if (row + column == 3) {
            QuartoPawn[] diagonal2 = new QuartoPawn[4];
            for (int i = 0; i < 4; i++) {
                diagonal2[i] = table[i][3 - i];
            }
            intersectingLines.add(diagonal2);
        }

        return intersectingLines;
    }

}