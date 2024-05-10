package src.controller;

import java.io.IOException;
import java.util.Map;
import src.model.QuartoModel;
import src.model.QuartoPawn;
import src.model.SlotManager;
import src.views.listeners.ViewModelListener;
import src.views.utils.FormatUtils;

public class ViewModelController implements ViewModelListener {
  QuartoModel quartoModel;
  private SlotManager slotManager;

  public ViewModelController() {
    this.slotManager = new SlotManager();
    this.slotManager.loadFromDirectory();
  }

  @Override
  public void createModel(int type1, int type2, String name1, String name2) {
    System.out.println("Creating model");
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

  /**
   * Get the current state of the board.
   *
   * @return A 4x4 matrix of strings representing the pawns on the board (null if
   *         the cell is empty)
   */
  public String[][] getTable() {
    QuartoPawn[][] table = quartoModel.getTable();
    String[][] tableStr = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (table[i][j] == null) {
          tableStr[i][j] = null;
        } else {
          tableStr[i][j] = FormatUtils.byteToString(table[i][j].getPawn());
        }
      }
    }
    return tableStr;
  }

  public void selectPawn(String pawnStr) {
    quartoModel.selectPawn(FormatUtils.stringToIndex(pawnStr));
  }

  /**
   * Return all the pawns still available to play.
   * 
   * @return an array of strings representing the code of pawns
   */
  public String[] getAvailablePawns() {
    // If the model is not created yet, return an empty array
    if (quartoModel == null) {
      return new String[0];
    }
    QuartoPawn[] pawns = quartoModel.getPawnAvailable();
    String[] pawnStrs = new String[pawns.length];
    for (int i = 0; i < pawns.length; i++) {
      if (pawns[i] != null) {
        pawnStrs[i] = FormatUtils.byteToString(pawns[i].getPawn());
      }
    }
    return pawnStrs;
  }

}
