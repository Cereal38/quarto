package src.controller;

import java.io.IOException;
import java.util.List;
import src.model.QuartoModel;
import src.model.QuartoPawn;
import src.model.SlotManager;
import src.structures.SlotFile;
import src.views.components.Pawn;
import src.views.game.board.Cell;
import src.views.listeners.ViewModelListener;
import src.views.utils.DimensionUtils;
import src.views.utils.FormatUtils;
import src.views.utils.PawnUtils;

public class ViewModelController implements ViewModelListener {
  QuartoModel quartoModel;
  private SlotManager slotManager;

  // TODO: Get it from the model
  // Game states
  public static final int SELECT = 0;
  public static final int PLAY = 1;

  public ViewModelController() {
    this.slotManager = new SlotManager();
    this.slotManager.loadFromDirectory();
  }

  @Override
  public void createModel(int type1, int type2, String name1, String name2) {
    this.quartoModel = new QuartoModel(type1, type2, name1, name2);
  }

  public QuartoModel getModel() {
    return this.quartoModel;
  }

  public void saveGame(String fileName) throws IOException {
    quartoModel.saveFile(fileName);
  }

  public void loadGame(int index) {
    this.quartoModel = new QuartoModel(index);
  }

  public boolean isSlotFileEmpty(int index) {
    return slotManager.isSlotFileEmpty(index);
  }

  @Override
  public List<SlotFile> getSlotFiles() {
    return slotManager.getSlotFiles();
  }

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

  public void undo() {
    quartoModel.undo();
  }

  public void redo() {
    quartoModel.redo();
  }

  public boolean canRedo() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.canRedo();
  }

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
        for (int[] win : winLine) {
          if (win[0] == i && win[1] == j) {
            isWinningCell = true;
            break;
          }
        }
        tableCells[i][j] = new Cell(pawn, i, j, isWinningCell);
      }
    }
    return tableCells;
  }

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
    QuartoPawn[] pawns = quartoModel.getPawnAvailable();
    int size = DimensionUtils.getBarCellSize();
    for (int i = 0; i < pawns.length; i++) {
      if (pawns[i] != null) {
        int index = FormatUtils.byteToIndex(pawns[i].getPawn());
        result[index] = PawnUtils.getPawn(FormatUtils.byteToString(pawns[i].getPawn()), Pawn.NOT_PLAYED, size, size);
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

  private QuartoPawn getSelectedPawn() {
    return quartoModel.getSelectedPawn();
  }

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

  public String getCurrentPlayerName() {
    if (quartoModel == null) {
      return null;
    }
    return quartoModel.getNameOfTheCurrentPlayer();
  }

  public String getPlayer1Name() {
    if (quartoModel == null) {
      return null;
    }
    return quartoModel.getPlayer1Name();
  }

  public String getPlayer2Name() {
    if (quartoModel == null) {
      return null;
    }
    return quartoModel.getPlayer2Name();
  }

  public int getPlayer1Type() {
    if (quartoModel == null) {
      return 0;
    }
    return quartoModel.getPlayer1Type();
  }

  public int getPlayer2Type() {
    if (quartoModel == null) {
      return 0;
    }
    return quartoModel.getPlayer2Type();
  }

  public boolean isPlayer1AI() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.getPlayer1Type() != 0;
  }

  public boolean isPlayer2AI() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.getPlayer2Type() != 0;
  }

  public boolean isSelectionPhase() {
    if (quartoModel == null) {
      return false;
    }
    return quartoModel.stateOfGame() == SELECT;
  }

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

}
