/**
 * The ViewModelController class serves as the controller in the Model-View-Controller (MVC) architecture
 * for managing the game state and interactions between the game model and the user interface.
 * It provides methods for creating, saving, and loading game states, as well as handling user actions
 * such as playing shots, selecting pawns, and checking for game-winning conditions.
 */
package src.controller;

import java.io.IOException;
import java.util.List;

import src.model.ai.Heuristics;
import src.model.ai.MiniMaxAIPlayer;
import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;
import src.model.game.SlotManager;
import src.structures.SlotFile;
import src.views.components.Pawn;
import src.views.game.board.Cell;
import src.views.listeners.ViewModelListener;
import src.views.utils.DimensionUtils;
import src.views.utils.FormatUtils;
import src.views.utils.PawnUtils;

public class ViewModelController implements ViewModelListener {
  /** The game model instance */
  QuartoModel quartoModel;

  /** Manages slot files for saving and loading game states */
  private SlotManager slotManager;

  /** Used to give the player a hint */
  private MiniMaxAIPlayer aiPlayer;

  // TODO: Get it from the model

  /**  Constants representing game states */
  public static final int SELECT = 0;
  public static final int PLAY = 1;

  /**
   * Constructs a new ViewModelController object.
   * Initializes the slot manager and loads slot files from the directory.
   */
  public ViewModelController() {
    aiPlayer = new MiniMaxAIPlayer(2, new Heuristics());
    this.slotManager = new SlotManager();
    this.slotManager.loadFromDirectory();
  }

  /**
   * Creates a new game model with the specified player types and names.
   *
   * @param type1  The type of player 1 (human or AI).
   * @param type2  The type of player 2 (human or AI).
   * @param name1  The name of player 1.
   * @param name2  The name of player 2.
   */
  @Override
  public void createModel(int type1, int type2, String name1, String name2) {
    this.quartoModel = new QuartoModel(type1, type2, name1, name2);
  }

  /**
   * Retrieves the current game model.
   *
   * @return The QuartoModel instance representing the current game state.
   */
  public QuartoModel getModel() {
    return this.quartoModel;
  }

  /**
   * Saves the current game state to a file with the specified name.
   *
   * @param fileName The name of the file to save the game state to.
   * @throws IOException If an I/O error occurs while saving the file.
   */
  public void saveGame(String fileName) throws IOException {
    quartoModel.saveFile(fileName);
  }

  /**
   * Loads a game state from the slot file with the specified index.
   *
   * @param index The index of the slot file to load.
   */
  public void loadGame(int index) {
    this.quartoModel = new QuartoModel(index);
  }


  /**
   * Retrieves the list of slot files available for saving and loading game states.
   *
   * @return A list of SlotFile objects representing the slot files.
   */
  @Override
  public List<SlotFile> getSlotFiles() {
    return slotManager.getSlotFiles();
  }

  /**
   * Clears the slot file with the specified ID.
   *
   * @param id The ID of the slot file to clear.
   */
  @Override
  public void clearSlot(int id) {
    slotManager.clearSlotFile(id);
  }


  /**
   * Plays a shot on the specified line and column.
   *
   * @param line   the line where the shot is played (0-3)
   * @param column the column where the shot is played (0-3)
   */
  public void playShot(int line, int column) {
    quartoModel.playShot(line, column);
  }

  /**
   * Undoes the last move in the game.
   */
  public void undo() {
    quartoModel.undo();
  }

  /**
   * Redoes the previously undone move in the game.
   */
  public void redo() {
    quartoModel.redo();
  }

  /**
   * Checks if it's possible to redo a move in the game.
   *
   * @return true if redo is possible, false otherwise.
   */
  public boolean canRedo() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.canRedo();
  }

  /**
   * Checks if it's possible to undo a move in the game.
   *
   * @return true if undo is possible, false otherwise.
   */
  public boolean canUndo() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.canUndo();
  }


  /**
   * Get the current state of the board.
   *
   * @return A 4x4 matrix of Cells
   */
  public Cell[][] getTable() {
    // If the model is not created yet, return an empty table
    if (quartoModel == null) {
      return new Cell[4][4];
    }
    List<int[]> winLine = quartoModel.getWinLine();

    int[] hint = playShotHint();
    QuartoPawn[][] pawns = quartoModel.getTable();
    Cell[][] tableCells = new Cell[4][4];
    int size = DimensionUtils.getBoardCellSize();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Pawn pawn = null;
        if (pawns[i][j] != null) {
          pawn = PawnUtils.getPawn(FormatUtils.byteToString(pawns[i][j].getPawn()), Pawn.PLAYED, size, size);
        }
        boolean isWinningCell = false;
        boolean isHint = false;
        for (int[] win : winLine) {
          if (win[0] == i && win[1] == j) {
            isWinningCell = true;
            break;
          }
        }
        if (hint != null && (hint[0] == i && hint[1]==j)){
          isHint = true;
        }
        tableCells[i][j] = new Cell(pawn, i, j, isWinningCell || isHint);
      }
    }
    return tableCells;
  }

  /**
   * Selects a pawn based on the provided pawn string representation.
   *
   * @param pawnStr The string representation of the pawn to select.
   */
  public void selectPawn(String pawnStr) {
    quartoModel.selectPawn(FormatUtils.stringToIndex(pawnStr));
  }

  /**
   * Return all the pawns still available to play.
   * 
   * @return An array of Pawns (null if no pawn available at this index)
   */
  public Pawn[] getAvailablePawns() {
    Pawn[] result = new Pawn[16];

    // If the model is not created yet, return an empty list
    if (quartoModel == null) {
      return result;
    }
    int hint = selectPawnHint();
    QuartoPawn[] pawns = quartoModel.getPawnAvailable();
    int size = DimensionUtils.getBarCellSize();
    for (int i = 0; i < pawns.length; i++) {
      if (pawns[i] != null) {
        int index = FormatUtils.byteToIndex(pawns[i].getPawn());
        if (hint != -1 && hint == i){
          result[index] = PawnUtils.getPawn(FormatUtils.byteToString(pawns[i].getPawn()), Pawn.HINT, size, size);
        } else {
          result[index] = PawnUtils.getPawn(FormatUtils.byteToString(pawns[i].getPawn()), Pawn.NOT_PLAYED, size, size);
        }
      } else {
        result[i] = null;
      }

    }
    // Add the selected pawn
    if (getSelectedPawn() != null) {
      byte selectedPawn = getSelectedPawn().getPawn();
      result[FormatUtils.byteToIndex(selectedPawn)] = PawnUtils.getPawn(FormatUtils.byteToString(selectedPawn),
          Pawn.SELECTED, size, size);
    }
    return result;
  }

  /**
   * Retrieves the currently selected pawn from the game model.
   *
   * @return The currently selected QuartoPawn.
   */
  private QuartoPawn getSelectedPawn() {
    return quartoModel.getSelectedPawn();
  }

  /**
   * Retrieves the string representation of the currently selected pawn.
   *
   * @return The string representation of the selected pawn, or null if no pawn is selected.
   */
  public String getSelectedPawnStr() {
    if (getSelectedPawn() == null) {
      return null;
    }
    return FormatUtils.byteToString(getSelectedPawn().getPawn());
  }


  /**
   * Get the current player. 1 for player 1, 2 for player 2.
   */
  public int getCurrentPlayer() {
    if (quartoModel == null) {
      return 0;
    }
    return quartoModel.getCurrentPlayer();
  }

  /**
   * Retrieves the name of the current player.
   *
   * @return The name of the current player, or null if the model is not initialized.
   */
  public String getCurrentPlayerName() {
    if (quartoModel == null) {
      return null;
    }
    return quartoModel.getNameOfTheCurrentPlayer();
  }

  /**
   * Retrieves the name of player 1.
   *
   * @return The name of player 1, or null if the model is not initialized.
   */
  public String getPlayer1Name() {
    if (quartoModel == null) {
      return null;
    }
    return quartoModel.getPlayer1Name();
  }

  /**
   * Retrieves the name of player 2.
   *
   * @return The name of player 2, or null if the model is not initialized.
   */
  public String getPlayer2Name() {
    if (quartoModel == null) {
      return null;
    }
    return quartoModel.getPlayer2Name();
  }

  /**
   * Retrieves the type of player 1.
   *
   * @return The type of player 1, or 0 if the model is not initialized.
   */
  public int getPlayer1Type() {
    if (quartoModel == null) {
      return 0;
    }
    return quartoModel.getPlayer1Type();
  }

  /**
   * Retrieves the type of player 2.
   *
   * @return The type of player 2, or 0 if the model is not initialized.
   */
  public int getPlayer2Type() {
    if (quartoModel == null) {
      return 0;
    }
    return quartoModel.getPlayer2Type();
  }

  /**
   * Checks if player 1 is controlled by an AI.
   *
   * @return true if player 1 is an AI, false otherwise.
   */
  public boolean isPlayer1AI() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.getPlayer1Type() != 0;
  }

  /**
   * Checks if player 2 is controlled by an AI.
   *
   * @return true if player 2 is an AI, false otherwise.
   */
  public boolean isPlayer2AI() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.getPlayer2Type() != 0;
  }

  /**
   * Checks if the game is in the selection phase.
   *
   * @return true if the game is in the selection phase, false otherwise.
   */
  public boolean isSelectionPhase() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.stateOfGame() == SELECT;
  }

  /**
   * Checks if the game is in the play phase.
   *
   * @return true if the game is in the play phase, false otherwise.
   */
  public boolean isPlayPhase() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.stateOfGame() == PLAY;
  }

  /**
   * Checks if the game is won by the player at the specified line and column.
   *
   * @param line   the line index of the selected position
   * @param column the column index of the selected position
   * @return true if the game is won, false otherwise
   */
  public boolean checkWinner(int line, int column) {
    return quartoModel.winSituation(line, column);
  }

  /**
   * Determines if it is the AI's turn to play.
   * 
   * @return true if it is the AI's turn, false otherwise
   */
  public boolean isCurrentPlayerAI() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.getCurrentPlayerType() != 0;
  }

  /**
   * Return true if the game is over because of a win.
   */
  public boolean isGameWon() {
    return quartoModel.hasAWinner();
  }

  /**
   * Return true if the game is over because of a draw.
   */
  public boolean isGameDraw() {
    return quartoModel.isATie();
  }

  /**
   * Return true if the game is over.
   */
  public boolean isGameOver() {
    return isGameDraw() || isGameWon();
  }

  /**
   * Overwrites an existing file with the provided file name in the Quarto game.
   *
   * @param fileName the name of the file to overwrite
   * @throws IOException if an I/O error occurs
   */
  public void overWriteFile(String fileName) throws IOException {
    quartoModel.overWriteFile(fileName);
  }

  /**
   * Provides a hint for playing the next shot by utilizing the AI to determine the best move.
   *
   * @return an array with the coordinates of the best move, where the first element is the row and
   *         the second element is the column.
   */
  public int[] playShotHint() {
    if (isPlayPhase())
      return aiPlayer.getBestMove(this.quartoModel);
    return null;
  }

  /**
   * Provides a hint for selecting the next pawn by utilizing the AI to determine the best pawn to select.
   *
   * @return the index of the best pawn to select.
   */
  public int selectPawnHint() {
    if (isSelectionPhase())
      return aiPlayer.getBestPawn(this.quartoModel);
    return -1;
  }
}
