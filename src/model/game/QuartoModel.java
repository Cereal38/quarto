/**
 * The QuartoModel class represents the model component of the Quarto game application.
 * It manages the game state, player interactions, and game logic.
 */
package src.model.game;

import java.io.File;
import java.io.IOException;
import java.util.List;

import src.model.ai.Heuristics;
import src.structures.SlotFile;

public class QuartoModel {

  /** The game board for the Quarto game. */
  private QuartoBoard board;

  /** The player manager for handling player interactions. */
  private QuartoPlayerManager playerManager;

  /** The file manager for saving and loading game states. */
  private QuartoFile file;

  /** The slot manager for managing game slots. */
  private SlotManager manager;

  /** Indicates whether the game is over. */
  private boolean gameOver = false;

  /**
   * Constructs a new QuartoModel instance with the specified index.
   * Initializes the game board, file manager, and slot manager.
   * Charges the game with the provided index.
   *
   * @param index The index of the game.
   */
  public QuartoModel(int index) {
    board = new QuartoBoard();
    file = new QuartoFile();
    manager = new SlotManager();
    chargeGame(index);
  }

  /**
   * Constructs a new QuartoModel instance without heuristics for non-MiniMax AI players.
   *
   * @param firstPlayerType  The type of the first player.
   * @param secondPlayerType The type of the second player.
   * @param firstPlayerName  The name of the first player.
   * @param secondPlayerName The name of the second player.
   */
  public QuartoModel(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName) {
    this(firstPlayerType, secondPlayerType, firstPlayerName, secondPlayerName, new Heuristics(), new Heuristics());
  }

  /**
   * Constructs a new QuartoModel instance with heuristics for MiniMax AI players.
   *
   * @param firstPlayerType  The type of the first player.
   * @param secondPlayerType The type of the second player.
   * @param firstPlayerName  The name of the first player.
   * @param secondPlayerName The name of the second player.
   * @param heuristic1       The heuristics for the first player.
   * @param heuristic2       The heuristics for the second player.
   */
  public QuartoModel(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName, Heuristics heuristic1, Heuristics heuristic2) {
    board = new QuartoBoard();
    file = new QuartoFile();
    manager = new SlotManager();
    playerManager = new QuartoPlayerManager(firstPlayerType, secondPlayerType, firstPlayerName, secondPlayerName, heuristic1, heuristic2);
  }

  /**
   * Gets the QuartoBoard instance associated with this model.
   *
   * @return The QuartoBoard instance.
   */
  public QuartoBoard getBoard() {
    return board;
  }

  /**
   * Gets the QuartoPlayerManager instance associated with this model.
   *
   * @return The QuartoPlayerManager instance.
   */
  public QuartoPlayerManager getPlayerManager() {
    return playerManager;
  }

  /**
   * Redoes the last undone action in the game.
   * If redo is available, updates the game state accordingly.
   */
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

  /**
   * Undoes the last action in the game.
   * If undo is available, updates the game state accordingly.
   */
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
        playerManager.switchPlayer();//the first player starts
      }
      file.setSave(file.getSave().getPrevious());
    }
  }

  /**
   * Selects a pawn with the specified index for the current player.
   *
   * @param indexPawn The index of the pawn to select.
   */
  public void selectPawn(int indexPawn) {
    playerManager.selectPawn(this, indexPawn);
  }

  /**
   * Allows a human player to select a pawn with the specified index and place it on the board.
   * Updates the game state accordingly.
   *
   * @param indexPawn The index of the pawn to select and place.
   */
  public void selectPawnHuman(int indexPawn) {
    if (board.getSelectedPawn() == null && !hasAWinner() && !isATie()) {
      board.setSelectedPawn(board.getPawnAvailable()[indexPawn]);
      file.getSave().setNext(new QuartoHistory(indexPawn, file.getSave(), playerManager.getNameOfTheCurrentPlayer(), playerManager.getCurrentPlayer()));
      file.getSave().getNext().setPrevious(file.getSave());
      file.setSave(file.getSave().getNext());
      board.getPawnAvailable()[indexPawn] = null;
      playerManager.switchPlayer(); // next player
    }
  }

  /**
   * Plays a shot at the specified position on the game board for the current player.
   *
   * @param line   The line index.
   * @param column The column index.
   */
  public void playShot(int line, int column) {
    playerManager.playShot(this, line, column);
  }

  /**
   * Allows a human player to play a shot at the specified position on the game board.
   * Updates the game state accordingly.
   *
   * @param line   The line index.
   * @param column The column index.
   */
  public void playShotHuman(int line, int column) {
    if (board.isTableEmpty(line, column) && board.getSelectedPawn() != null && !hasAWinner() && !isATie()) {
      board.setTable(line, column, board.getSelectedPawn());
      gameOver = winSituation(line, column);
      board.setSelectedPawn(null);
      file.getSave().setNext(new QuartoHistory(line, column, file.getSave(), playerManager.getNameOfTheCurrentPlayer(), playerManager.getCurrentPlayer()));
      file.getSave().getNext().setPrevious(file.getSave());
      file.setSave(file.getSave().getNext());
    }
  }

  /**
   * Redoes a sequence of undo actions specified by the countUndo parameter.
   *
   * @param countUndo The number of undo actions to redo.
   */
  public void redoLoop(int countUndo) {
    while (countUndo != 0) {
      redo();
      countUndo--;
    }
  }

  /**
   * Checks the information of the player from the specified index.
   * Loads player information from the slot files directory.
   * Initializes the player manager with the loaded information.
   *
   * @param index The index of the player's information to check.
   * @return True if player information is successfully loaded, false otherwise.
   */
  public boolean checkInfoPlayer(int index) {
    if (playerManager == null)
      playerManager = new QuartoPlayerManager();
    int countUndo = 0;
    manager.loadFromDirectory();
    List<SlotFile> slotFiles = manager.getSlotFiles();
    if (index < 0 || index >= slotFiles.size()) {
      System.err.println("Invalid index: " + index);
      return false;
    }

    SlotFile slotFile = slotFiles.get(index);
    String fileName = slotFile.getFilename();

    while (canUndo()) {
      undo();
      countUndo++;
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
    playerManager.setPlayer2Name(nameAndType[0]);
    playerManager.setPlayer2Type(Integer.parseInt(nameAndType[1]));

    playerManager.initializeAIPlayers();


    return true;
  }


  /**
   * Charges the game state based on the player information from the specified index.
   * If successful, updates the game board and player manager accordingly.
   *
   * @param index The index of the player's information to charge.
   */
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

  /**
   * Saves the game to a file with the provided file name in the Quarto game.
   * If the file already exists, it throws an IOException.
   *
   * @param fileName the name of the file to save
   * @throws IOException if an I/O error occurs or if the file already exists
   */
  public void saveFile(String fileName) throws IOException {
    String filePath = "slots" + File.separator + fileName;
    try {
      manager.createNewFile(fileName);
      file.saveFile(filePath, playerManager.getPlayer1Name(), playerManager.getPlayer2Name(), playerManager.getPlayerType());
    } catch (IOException e) {
      throw e;
    }
  }


  /**
   * Overwrites an existing file with the provided file name in the Quarto game.
   *
   * @param fileName the name of the file to overwrite
   * @throws IOException if an I/O error occurs
   */
  public void overWriteFile(String fileName) throws IOException {
    String filePath = "slots" + File.separator + fileName;
    file.saveFile(filePath, playerManager.getPlayer1Name(), playerManager.getPlayer2Name(), playerManager.getPlayerType());
  }



  /**
   * Checks if the game has a winner.
   *
   * @return True if there is a winner, false otherwise.
   */
  public boolean hasAWinner() {
    return gameOver;
  }

  /**
   * Checks if the game is tied.
   *
   * @return True if the game is tied, false otherwise.
   */
  public boolean isATie() {
    return (board.getSelectedPawn() == null && board.isPawnListEmpty() && !hasAWinner());
  }

  /**
   * Retrieves the QuartoPawn at the specified index from the available pawns list.
   *
   * @param pawnIndex The index of the pawn to retrieve.
   * @return The QuartoPawn at the specified index.
   */
  public QuartoPawn getPawn(int pawnIndex) {
    return board.getPawnAvailable()[pawnIndex];
  }

  /**
   * Retrieves the current state of the game.
   *
   * @return The current state of the game as a QuartoHistory object.
   */
  public QuartoHistory getCurrentState() {
    return file.getSave();
  }

  /**
   * Retrieves the initial state of the game.
   *
   * @return The initial state of the game as a QuartoHistory object.
   */
  public QuartoHistory getFirstState() {
    return file.getHead();
  }

  /**
   * Checks if undo action is possible.
   *
   * @return True if undo action is possible, false otherwise.
   */
  public boolean canUndo() {
    return file.canUndo();
  }

  /**
   * Checks if redo action is possible.
   *
   * @return True if redo action is possible, false otherwise.
   */
  public boolean canRedo() {
    return file.canRedo();
  }

  /**
   * Retrieves the state of the game.
   *
   * @return The state of the game:
   *         - 0 if it's the beginning of the game,
   *         - 1 if the game is ongoing.
   */
  public int stateOfGame() {
    return (file.getState() == 0) ? 1 : 0;
  }

  /**
   * Delays the execution of the program for the specified number of milliseconds.
   * Used for AI plays
   *
   * @param ms The number of milliseconds to delay the execution.
   */
  public void delay(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the array of available pawns on the board.
   *
   * @return An array of QuartoPawn objects representing available pawns.
   */
  public QuartoPawn[] getPawnAvailable() {
    return board.getPawnAvailable();
  }

  /**
   * Retrieves the 2D array representing the current state of the game board.
   *
   * @return A 2D array of QuartoPawn objects representing the game board.
   */
  public QuartoPawn[][] getTable() {
    return board.getTable();
  }

  /**
   * Checks if the specified position on the game board is empty.
   *
   * @param i The row index.
   * @param j The column index.
   * @return True if the position is empty, false otherwise.
   */
  public boolean isTableEmpty(int i, int j) {
    return board.isTableEmpty(i,j);
  }

  /**
   * Retrieves the currently selected pawn.
   *
   * @return The currently selected QuartoPawn.
   */
  public QuartoPawn getSelectedPawn() {
    return board.getSelectedPawn();
  }

  /**
   * Retrieves the index of the current player.
   *
   * @return The index of the current player.
   */
  public int getCurrentPlayer() {
    return playerManager.getCurrentPlayer();
  }

  /**
   * Checks if the list of available pawns is empty.
   *
   * @return True if the list of available pawns is empty, false otherwise.
   */
  public boolean isPawnListEmpty() {
    return board.isPawnListEmpty();
  }

  /**
   * Retrieves the lines of the game board.
   *
   * @return A 2D array of QuartoPawn objects representing the lines of the game board.
   */
  public QuartoPawn[][] getLines() {
    return board.getLines();
  }

  /**
   * Retrieves the columns of the game board.
   *
   * @return A 2D array of QuartoPawn objects representing the columns of the game board.
   */
  public QuartoPawn[][] getColumns() {
    return board.getColumns();
  }

  /**
   * Retrieves the diagonals of the game board.
   *
   * @return A 2D array of QuartoPawn objects representing the diagonals of the game board.
   */
  public QuartoPawn[][] getDiagonals() {
    return board.getDiagonals();
  }

  /**
   * Retrieves the list of win lines on the game board.
   * Each win line is represented by an array of integers containing the indices of the winning positions.
   *
   * @return A list of arrays representing the win lines.
   */
  public List<int[]> getWinLine() {
    return board.getWinLine();
  }

  /**
   * Retrieves the name of the current player.
   *
   * @return The name of the current player.
   */
  public String getNameOfTheCurrentPlayer() {
    return playerManager.getNameOfTheCurrentPlayer();
  }

  /**
   * Retrieves the name of player 1.
   *
   * @return The name of player 1.
   */
  public String getPlayer1Name() {
    return playerManager.getPlayer1Name();
  }

  /**
   * Retrieves the name of player 2.
   *
   * @return The name of player 2.
   */
  public String getPlayer2Name() {
    return playerManager.getPlayer2Name();
  }

  /**
   * Retrieves the type of player 1.
   *
   * @return The type of player 1.
   */
  public int getPlayer1Type() {
    return playerManager.getPlayer1Type();
  }

  /**
   * Retrieves the type of player 2.
   *
   * @return The type of player 2.
   */
  public int getPlayer2Type() {
    return playerManager.getPlayer2Type();
  }

  /**
   * Checks if a winning situation occurs after placing a pawn at the specified position.
   *
   * @param line   The line index.
   * @param column The column index.
   * @return True if a winning situation occurs, false otherwise.
   */
  public boolean winSituation(int line, int column) {
    return board.winSituation(line, column);
  }

  /**
   * Retrieves the type of the current player.
   *
   * @return The type of the current player.
   */
  public int getCurrentPlayerType() {
    return playerManager.getCurrentPlayerType();
  }

  /**
   * Sets the currently selected pawn on the board.
   *
   * @param quartoPawn The pawn to set as selected.
   */
  public void setSelectedPawn(QuartoPawn quartoPawn) {
    board.setSelectedPawn(quartoPawn);
  }

  /**
   * Switches the current player to the next player.
   */
  public void switchPlayer() {
    playerManager.switchPlayer();
  }

  /**
   * Retrieves the pawn at the specified position on the game board.
   *
   * @param i The row index.
   * @param j The column index.
   * @return The QuartoPawn at the specified position.
   */
  public QuartoPawn getPawnAtPosition(int i, int j) {
    return board.getPawnAtPosition(i,j);
  }

  /**
   * Retrieves the current save state of the game.
   *
   * @return The current save state of the game as a QuartoHistory object.
   */
  public QuartoHistory getSave() {
    return file.getSave();
  }


}
