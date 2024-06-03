package src.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import src.structures.SlotFile;

public class QuartoModel {
  private QuartoBoard board;
  private QuartoPlayerManager playerManager;
  private QuartoFile file;
  private SlotManager manager;
  private boolean gameOver = false; // true if end game

  public QuartoModel(int index) {
    board = new QuartoBoard();
    file = new QuartoFile();
    manager = new SlotManager();
    chargeGame(index);
  }

  // Constructor without heuristics (for non-MiniMax AI players)
  public QuartoModel(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName) {
    this(firstPlayerType, secondPlayerType, firstPlayerName, secondPlayerName, new Heuristics(), new Heuristics());
  }

  // Constructor with heuristics (for MiniMax AI players)
  public QuartoModel(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName, Heuristics heuristic1, Heuristics heuristic2) {
    board = new QuartoBoard();
    file = new QuartoFile();
    manager = new SlotManager();
    playerManager = new QuartoPlayerManager(firstPlayerType, secondPlayerType, firstPlayerName, secondPlayerName, heuristic1, heuristic2);
  }

  public QuartoBoard getBoard() {
    return board;
  }

  public QuartoPlayerManager getPlayerManager() {
    return playerManager;
  }

  public void redo() {
    if (file.canRedo()) {
      if (file.getNextState() == 0) { // choice of pawn
        board.setSelectedPawn(board.getPawnAvailable()[file.getNextIndexPawn()]);
        board.getPawnAvailable()[file.getNextIndexPawn()] = null;
        playerManager.switchPlayer(); // next player
      } else if (file.getNextState() == 1) { // choice of place
        board.setTable(file.getNextLine(), file.getNextColumn(), board.getSelectedPawn());
        board.setSelectedPawn(null);
        board.winSituation(file.getNextLine(), file.getNextColumn());
      }
      file.setSave(file.getSave().getNext());
    }
  }

  public void undo() {
    if (file.canUndo()) {
      board.getWin().clearWinLine();
      if (file.getPreviousState() == 0) { // we remove a placed pawn
        board.setSelectedPawn(board.getPawnAtPosition(file.getLine(), file.getColumn()));
        board.setTable(file.getLine(), file.getColumn(), null);
        if (gameOver) {
          gameOver = false;
        }
      } else if (file.getPreviousState() == 1) { // we add the pawn chosen to the list of pawn available.
        board.getPawnAvailable()[file.getIndexPawn()] = board.getSelectedPawn();
        board.setSelectedPawn(null);
        playerManager.switchPlayer(); // next player
      } else { // before the first move
        board.getPawnAvailable()[file.getIndexPawn()] = board.getSelectedPawn();
        board.setSelectedPawn(null);
      }
      file.setSave(file.getSave().getPrevious());
    }
  }

  public void selectPawn(int indexPawn) {
    playerManager.selectPawn(this, indexPawn);
  }

  public void selectPawnHuman(int indexPawn) {
    if (board.getSelectedPawn() == null) {
      board.setSelectedPawn(board.getPawnAvailable()[indexPawn]);
      file.getSave().setNext(new QuartoHistory(indexPawn, file.getSave(), playerManager.getNameOfTheCurrentPlayer(), playerManager.getCurrentPlayer()));
      file.getSave().getNext().setPrevious(file.getSave());
      file.setSave(file.getSave().getNext());
      board.getPawnAvailable()[indexPawn] = null;
      playerManager.switchPlayer(); // next player
    }
  }

  public void playShot(int line, int column) {
    playerManager.playShot(this, line, column);
  }

  public void playShotHuman(int line, int column) {
    if (board.isTableEmpty(line, column) && board.getSelectedPawn() != null) {
      board.setTable(line, column, board.getSelectedPawn());
      board.winSituation(line, column);
      board.setSelectedPawn(null);
      file.getSave().setNext(new QuartoHistory(line, column, file.getSave(), playerManager.getNameOfTheCurrentPlayer(), playerManager.getCurrentPlayer()));
      file.getSave().getNext().setPrevious(file.getSave());
      file.setSave(file.getSave().getNext());
    }
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
    List<SlotFile> slotFiles = manager.getSlotFiles();
    if (index < 0 || index >= slotFiles.size()) {
      System.err.println("Invalid index: " + index);
      return false;
    }

    SlotFile slotFile = slotFiles.get(index);
    String fileName = slotFile.getFilename();

    while (file.canUndo()) {
      undo();
      countUndo++;
    }
    System.out.println("Number of files = " + slotFiles.size());

    if (manager.isSlotFileEmpty(slotFile.getId())) {
      System.err.println("Index " + index + " contains a empty file: " + fileName);
      redoLoop(countUndo);
      return false;
    }

    String[] infoPlayer = file.chargeFile(fileName);
    if (infoPlayer == null) {
      redoLoop(countUndo);
      return false;
    }

    String[] nameAndType = infoPlayer[0].split(" ");
    if (nameAndType.length != 2) {
      System.err.println("Missing information for player 1");
      redoLoop(countUndo);
      return false;
    }
    playerManager.setPlayer1Name(nameAndType[0]);
    playerManager.setPlayer1Type(Integer.parseInt(nameAndType[1]));


    nameAndType = infoPlayer[1].split(" ");
    if (nameAndType.length != 2) {
      System.err.println("Missing information for player 2");
      redoLoop(countUndo);
      return false;
    }
    playerManager.setPlayer1Name(nameAndType[0]);
    playerManager.setPlayer1Type(Integer.parseInt(nameAndType[1]));


    return true;
  }

  public void chargeGame(int index) {
    if (!checkInfoPlayer(index))
      return;
    QuartoHistory copy = file.getHead();
    boolean afterSave = false;
    while (!afterSave) {
      if (copy.state == 0) {
        board.setSelectedPawn(board.getPawnAvailable()[copy.getIndexPawn()]);
        board.getPawnAvailable()[copy.getIndexPawn()] = null;
      } else if (copy.state == 1) {
        board.setTable(copy.getLine(), copy.getColumn(), board.getSelectedPawn());
        board.winSituation(copy.getLine(), copy.getColumn());
        board.setSelectedPawn(null);
      }
      if (copy.equals(file.getSave())) {
        afterSave = true;
      }
      copy = copy.getNext();
    }
  }

  public void saveFile(String fileName) throws IOException {
    String filePath = "slots" + File.separator + fileName;
    manager.createNewFile(fileName);
    file.saveFile(filePath, playerManager.getPlayer1Name(), playerManager.getPlayer2Name(), playerManager.getPlayerType());
  }

  public boolean hasAWinner() {
    return gameOver;
  }

  public boolean isATie() {
    return (board.getSelectedPawn() == null && board.isPawnListEmpty());
  }

  public QuartoPawn getPawn(int pawnIndex) {
    return board.getPawnAvailable()[pawnIndex];
  }

  public QuartoHistory getCurrentState() {
    return file.getSave();
  }

  public QuartoHistory getFirstState() {
    return file.getHead();
  }

  public boolean canUndo() {
    return file.canUndo();
  }

  public boolean canRedo() {
    return file.canRedo();
  }

  public int stateOfGame() {
    return (file.getState() == 0) ? 1 : 0;
  }

  public void delay(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public QuartoPawn[] getPawnAvailable() {
    return board.getPawnAvailable();
  }

  public QuartoPawn[][] getTable() {
    return board.getTable();
  }

  public boolean isTableEmpty(int i, int j) {
    return board.isTableEmpty(i,j);
  }

  public QuartoPawn getSelectedPawn() {
    return board.getSelectedPawn();
  }

  public int getCurrentPlayer() {
    return playerManager.getCurrentPlayer();
  }

  public boolean isPawnListEmpty() {
    return board.isPawnListEmpty();
  }

  public QuartoPawn[][] getLines() {
    return board.getLines();
  }

  public QuartoPawn[][] getColumns() {
    return board.getColumns();
  }

  public QuartoPawn[][] getDiagonals() {
    return board.getDiagonals();
  }

  public List<int[]> getWinLine() {
    return board.getWinLine();
  }

  public String getNameOfTheCurrentPlayer() {
    return playerManager.getNameOfTheCurrentPlayer();
  }

  public String getPlayer1Name() {
    return playerManager.getPlayer1Name();
  }

  public String getPlayer2Name() {
    return playerManager.getPlayer2Name();
  }

  public int getPlayer1Type() {
    return playerManager.getPlayer1Type();
  }

  public int getPlayer2Type() {
    return playerManager.getPlayer2Type();
  }

  public boolean winSituation(int line, int column) {
    return board.winSituation(line, column);
  }

  public int getCurrentPlayerType() {
    return playerManager.getCurrentPlayerType();
  }

  public void setSelectedPawn(QuartoPawn quartoPawn) {
    board.setSelectedPawn(quartoPawn);
  }

  public void switchPlayer() {
    playerManager.switchPlayer();
  }

  public QuartoPawn getPawnAtPosition(int i, int i1) {
    return board.getPawnAtPosition(i,i1);
  }

  public QuartoHistory getSave() {
    return file.getSave();
  }
}
