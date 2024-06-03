package src.views.listeners;

import src.model.game.QuartoModel;
import src.structures.SlotFile;

import java.io.IOException;
import java.util.List;

public interface ViewModelListener {
    public void createModel(int type1, int type2, String name1, String name2);
    public QuartoModel getModel();

    public void saveGame(String fileName) throws IOException ;

    public void loadGame(int index);

    List<SlotFile> getSlotFiles();

    void clearSlot(int id);
}
