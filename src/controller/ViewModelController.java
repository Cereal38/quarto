package src.controller;

import java.io.IOException;
import java.util.Map;
import src.model.QuartoModel;
import src.model.SlotManager;
import src.views.listeners.ViewModelListener;

public class ViewModelController implements ViewModelListener {
  QuartoModel quartoModel;
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
    System.out.println("ViewModelController.playShot() " + quartoModel);
    quartoModel.playShot(line, column);
  }

}
