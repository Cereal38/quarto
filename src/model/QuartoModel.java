package src.model;

public class QuartoModel {
    private QuartoPawn[][] table;
    private int player;//1 for Player 1 and 2 for Player 2
    private int playerType[] = new int [2] ; // 0 for Human and 1 for AI
    //with playerType[0] type of the player 1 and playerType[1] type of the player 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    QuartoFile histo;
    QuartoWin win;

    private Player aiPlayer;

    public QuartoModel(int firstPlayerType, int secondPlayerType) {
        newTable(firstPlayerType, secondPlayerType);
        histo = new QuartoFile();
        win = new QuartoWin();
        if(firstPlayerType == 1){
            aiPlayer = new RandomAIPlayer();
        }else if (secondPlayerType == 1){
            aiPlayer = new RandomAIPlayer();
        }
    }

    private void newTable(int firstPlayerType, int secondPlayerType) {
        table = new QuartoPawn[4][4];//table filled with null
        player = 1;//starting player is player 1
        playerType[0] = firstPlayerType;
        playerType[1] = secondPlayerType;
        pawnAvailable = new QuartoPawn[16];
        for (int count = 0; count < 16; count++) {
            pawnAvailable[count] = new QuartoPawn(count);
        }
    }

    public void redo() {
        if (histo.canRedo()) {
            if (histo.getNextState() == 0) {//choice of pawn
                setSelectedPawn(pawnAvailable[histo.getNextIndexPawn()]);
                pawnAvailable[histo.getNextIndexPawn()] = null;
                switchPlayer();//next player
            } else if (histo.getNextState() == 1) { //choice of place
                setTable(histo.getNextLine(), histo.getNextColumn(), getSelectedPawn());
                setSelectedPawn(null);
            }
            histo.setSave(histo.getSave().getNext());
        }
    }

    public void undo() {
        if (histo.canUndo()) {
            if (histo.getPreviousState() == 0) {//we remove a placed pawn
                setSelectedPawn(getPawnAtPosition(histo.getLine(), histo.getColumn()));
                setTable(histo.getLine(), histo.getColumn(), null);
            } else if (histo.getPreviousState() == 1) {//we add the pawn chosen to the list of pawn available.
                pawnAvailable[histo.getIndexPawn()] = getSelectedPawn();
                setSelectedPawn(null);
                switchPlayer();//next player
            }
            histo.setSave(histo.getSave().getPrevious());
        }
    }

    public void selectPawn(int indexPawn) {
        if(getCurrentPlayerType() == 1){
            aiPlayer.selectPawn(this);
        } else {
            selectPawnHuman(indexPawn);
        }
    }

    public void selectPawnHuman(int indexPawn){
        setSelectedPawn(pawnAvailable[indexPawn]);
        //Add a new history because we chose what pawn the next player will play.
        histo.getSave().setNext(new QuartoHistory(indexPawn, histo.getSave()));
        histo.getSave().getNext().setPrevious(histo.getSave());
        histo.setSave(histo.getSave().getNext());
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
        player = (player == 1) ? 2 : 1;
    }

    public void playShot(int line, int column) {
        if (getCurrentPlayerType() == 1){
            aiPlayer.playShot(this);
        } else {
            playShotHuman(line, column);
        }
    }

    public void playShotHuman(int line, int column){
        if (isTableEmpty(line, column)) {
            setTable(line, column, selectedPawn);
            winSituation(line, column);
            setSelectedPawn(null);
            histo.getSave().setNext(new QuartoHistory(line, column, histo.getSave()));
            histo.getSave().getNext().setPrevious(histo.getSave());
            histo.setSave(histo.getSave().getNext());
        }
    }

    public boolean winSituation(int line, int column) {

        return (win.winSituationLine(getTable(), line) || win.winSituationColumn(getTable(), column)
                || win.winSituationDiagonal(getTable(), line, column));
    }

    public void chargeGame(String fileName) {
        histo.chargeFile(fileName);
        QuartoHistory copy = histo.head;
        while(!copy.equals(histo.save)) {
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

    public QuartoPawn[][] getTable() {
        return table;
    }

    public int getCurrentPlayer() {
        return player;
    }

    public QuartoFile getHisto() {
        return histo;
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