package src.model;

import java.io.IOException;

public class QuartoModel {
    private QuartoPawn[][] table;
    private int currentPlayer;//1 for Player 1 and 2 for Player 2
    private int playerType[] = new int [2] ; // 0 for Human and 1 for Random AI
    //with playerType[0] type of the player 1 and playerType[1] type of the player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    private QuartoFile file;
    private QuartoWin win;

    private Player randomAIPlayer;

    public QuartoModel(int firstPlayerType, int secondPlayerType) {
        newTable(firstPlayerType, secondPlayerType);
        file = new QuartoFile();
        win = new QuartoWin();
        if(firstPlayerType == 1){
            randomAIPlayer = new RandomAIPlayer();
        }else if (secondPlayerType == 1){
            randomAIPlayer = new RandomAIPlayer();
        }
    }

    private void newTable(int firstPlayerType, int secondPlayerType) {
        table = new QuartoPawn[4][4];//table filled with null
        currentPlayer = 1;//starting player is player 1
        playerType[0] = firstPlayerType;
        playerType[1] = secondPlayerType;
        pawnAvailable = new QuartoPawn[16];
        for (int count = 0; count < 16; count++) {
            pawnAvailable[count] = new QuartoPawn(count);
        }
    }

    public void redo() {
        if (file.canRedo()) {
            if (file.getNextState() == 0) {//choice of pawn
                setSelectedPawn(pawnAvailable[file.getNextIndexPawn()]);
                pawnAvailable[file.getNextIndexPawn()] = null;
                switchPlayer();//next player
            } else if (file.getNextState() == 1) { //choice of place
                setTable(file.getNextLine(), file.getNextColumn(), getSelectedPawn());
                setSelectedPawn(null);
            }
            file.setSave(file.getSave().getNext());
        }
    }

    public void undo() {
        if (file.canUndo()) {
            if (file.getPreviousState() == 0) {//we remove a placed pawn
                setSelectedPawn(getPawnAtPosition(file.getLine(), file.getColumn()));
                setTable(file.getLine(), file.getColumn(), null);
            } else if (file.getPreviousState() == 1) {//we add the pawn chosen to the list of pawn available.
                pawnAvailable[file.getIndexPawn()] = getSelectedPawn();
                setSelectedPawn(null);
                switchPlayer();//next player
            }
            file.setSave(file.getSave().getPrevious());
        }
    }

    public void selectPawn(int indexPawn) {
        if(getCurrentPlayerType() == 1){
            randomAIPlayer.selectPawn(this);
        } else {
            selectPawnHuman(indexPawn);
        }
    }

    public void selectPawnHuman(int indexPawn){
        setSelectedPawn(pawnAvailable[indexPawn]);
        //Add a new history because we chose what pawn the next player will play.
        file.getSave().setNext(new QuartoHistory(indexPawn, file.getSave()));
        file.getSave().getNext().setPrevious(file.getSave());
        file.setSave(file.getSave().getNext());
        pawnAvailable[indexPawn] = null;
        switchPlayer();//next player
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
        if (getCurrentPlayerType() == 1){
            randomAIPlayer.playShot(this);
        } else {
            playShotHuman(line, column);
        }
    }

    public void playShotHuman(int line, int column){
        if (isTableEmpty(line, column)) {
            setTable(line, column, selectedPawn);
            winSituation(line, column);
            setSelectedPawn(null);
            file.getSave().setNext(new QuartoHistory(line, column, file.getSave()));
            file.getSave().getNext().setPrevious(file.getSave());
            file.setSave(file.getSave().getNext());
        }
    }

    public boolean winSituation(int line, int column) {

        return (win.winSituationLine(getTable(), line) || win.winSituationColumn(getTable(), column)
                || win.winSituationDiagonal(getTable(), line, column));
    }

    public void chargeGame(String fileName) {
        file.chargeFile(fileName);
        QuartoHistory copy = file.getHead();
        while(!copy.equals(file.getSave())) {
            if (copy.state == 0) {
                selectedPawn = pawnAvailable[copy.getIndexPawn()];
                pawnAvailable[copy.getIndexPawn()] = null;
            } else if (copy.state == 1) {
                setTable(copy.getLine(), copy.getColumn(), getSelectedPawn());
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

    public void saveFile(String fileName) throws IOException {
        file.saveFile(fileName);
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

    public QuartoPawn[] getPawnAvailable(){
        return pawnAvailable;
    }

    public int getCurrentPlayerType(){
        return playerType[getCurrentPlayer() - 1];
    }
}