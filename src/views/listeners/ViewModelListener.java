package src.views.listeners;

import src.model.QuartoModel;

import java.io.IOException;
import java.util.Map;

public interface ViewModelListener {
    public void createModel(int type1, int type2, String name1, String name2);
    public QuartoModel getModel();

    public Map<String, Long> getSlotFileDates();


    public void saveGame(String fileName) throws IOException ;

    public void loadGame(int index);
    public boolean isSlotFileEmpty(int index);
}
