package src.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import src.model.QuartoFile;
import src.model.QuartoModel;
import src.model.QuartoPawn;
import src.model.SlotManager;
import src.views.components.Pawn;
import src.views.game.board.Cell;
import src.views.listeners.ViewModelListener;
import src.views.utils.FormatUtils;

public class ViewModelController implements ViewModelListener {
  QuartoModel quartoModel;
  QuartoFile quartoFile;
  private SlotManager slotManager;

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
    quartoModel.saveFile(fileName);
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
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Pawn pawn = null;
        if (pawns[i][j] != null) {
          pawn = new Pawn(FormatUtils.byteToString(pawns[i][j].getPawn()), 50, 50);
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
   * @return A list of pawns
   */
  public List<Pawn> getAvailablePawns() {
    // If the model is not created yet, return an empty list
    if (quartoModel == null) {
      return new ArrayList<>();
    }
    QuartoPawn[] pawns = quartoModel.getPawnAvailable();
    List<Pawn> pawnList = new ArrayList<>();
    for (int i = 0; i < pawns.length; i++) {
      if (pawns[i] != null) {
        pawnList.add(new Pawn(FormatUtils.byteToString(pawns[i].getPawn()), 50, 50));
      }
    }
    return pawnList;
  }

  public String getCurrentPlayerName() {
    return "Player name";
  }

  public int getCurrentState() {
    return -1;
  }

}
