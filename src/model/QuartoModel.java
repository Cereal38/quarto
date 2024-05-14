package src.model;

import java.io.IOException;

public class QuartoModel {
    private QuartoPawn[][] table;
    private int currentPlayer;// 1 for Player 1 and 2 for Player 2
    private int[] playerType = new int[2]; // 0 for Human and 1 for Random AI
    // with playerType[0] type of the player 1 and playerType[1] type of the player
    // 2
    private QuartoPawn[] pawnAvailable;
    private QuartoPawn selectedPawn;
    private QuartoFile file;
    private QuartoWin win;
    private SlotManager manager;
    private String firstPlayerName, secondPlayerName;

    private Player randomAIPlayer;

    public QuartoModel(int index) {
        newTable(0, 0);
        file = new QuartoFile();
        win = new QuartoWin();
        manager = new SlotManager();
        chargeGame(index);
        if (playerType[0] == 1 || playerType[1] == 1) {
            randomAIPlayer = new RandomAIPlayer();
        }
    }

    public QuartoModel(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName) {
        newTable(firstPlayerType, secondPlayerType);
        file = new QuartoFile();
        win = new QuartoWin();
        manager = new SlotManager();
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        // System.out.println("name 1 : " + firstPlayerName + "name 2 :" +
        // secondPlayerName);
        if (firstPlayerType == 1 || secondPlayerType == 1) {
            randomAIPlayer = new RandomAIPlayer();
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

    public void selectPawn(int indexPawn) {
        if (pawnAvailable[indexPawn] != null) {
            if (getCurrentPlayerType() == 1) {
                randomAIPlayer.selectPawn(this);
            } else {
                selectPawnHuman(indexPawn);
            }
        }
    }

    public void selectPawnHuman(int indexPawn) {
        setSelectedPawn(pawnAvailable[indexPawn]);
        // Add a new history because we chose what pawn the next player will play.
        file.getSave().setNext(new QuartoHistory(indexPawn, file.getSave()));
        file.getSave().getNext().setPrevious(file.getSave());
        file.setSave(file.getSave().getNext());
        pawnAvailable[indexPawn] = null;
        switchPlayer();// next player
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
        } else {
            playShotHuman(line, column);
        }
    }

    public void playShotHuman(int line, int column) {
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

    public void redoLoop(int countUndo) {
        while (countUndo != 0) {
            redo();
            countUndo--;
        }
    }

    public boolean goodInfoPlayer(int index) {
        int countUndo = 0;
        manager.loadFromDirectory();
        while (file.canUndo()) {
            undo();
            countUndo++;
        }
        System.out.println("nb files =" + manager.getSlotFileDates().size());
        if (manager.isSlotFileEmpty(index) == true) {
            System.err.println("l'index: " + index + " contient "
                    + manager.getSlotFileDates().keySet().toArray(new String[0])[index] + " un fichier vide");
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
        if (goodInfoPlayer(index) == false)
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
}