package src.controller;

import java.io.IOException;
import java.util.Map;
import src.model.QuartoFile;
import src.model.QuartoModel;
import src.model.QuartoPawn;
import src.model.SlotManager;
import src.views.components.Pawn;
import src.views.game.board.Cell;
import src.views.listeners.ViewModelListener;
import src.views.utils.DimensionUtils;
import src.views.utils.FormatUtils;
import src.views.utils.PawnUtils;

public class ViewModelController implements ViewModelListener {
  QuartoModel quartoModel;
  QuartoFile quartoFile;
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

  public Map<String, Long> getSlotFileDates() {
    return slotManager.getSlotFileDates();
  }

  public void saveGame(String fileName) throws IOException {
    // quartoModel.saveFile(fileName);
  }

  public void loadGame(int index) {
    quartoModel.chargeGame(index);
  }

  public boolean isSlotFileEmpty(int index) {
    return slotManager.isSlotFileEmpty(index);
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
    return quartoFile.canRedo();
  }

  public boolean canUndo() {
    return quartoFile.canUndo();
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
    QuartoPawn[][] pawns = quartoModel.getTable();
    Cell[][] tableCells = new Cell[4][4];
    int size = DimensionUtils.getBoardCellSize();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Pawn pawn = null;
        if (pawns[i][j] != null) {
          pawn = PawnUtils.getPawn(FormatUtils.byteToString(pawns[i][j].getPawn()), Pawn.PLAYED, size, size);
        }
        tableCells[i][j] = new Cell(pawn, i, j);
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

  public String getCurrentPlayerName() {
    return isCurrentPlayerAI() ? "AI" : "Player";
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

  public boolean isGameFinished(int line, int column) {
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

}
