package src.controller;

import src.model.QuartoModel;
import src.model.SlotManager;
import src.views.listeners.ViewModelListener;

import java.io.IOException;
import java.util.Map;

public class ViewModelController implements ViewModelListener {
    QuartoModel quartoModel;
    private SlotManager slotManager;

    public ViewModelController(){
        this.slotManager = new SlotManager();
        this.slotManager.loadFromDirectory();
    }

    @Override
    public void createModel(int type1, int type2, String name1, String name2) {
        this.quartoModel = new QuartoModel(type1, type2, name1, name2);
    }

    public QuartoModel getModel(){
        return this.quartoModel;
    }

    public Map<String, Long> getSlotFileDates() {
        return slotManager.getSlotFileDates();
    }

    public void saveGame(String fileName) throws IOException {
        quartoModel.saveFile(fileName);
    }

    public void loadGame(int index){
        quartoModel.chargeGame(index);
    }

    public boolean isSlotFileEmpty(int index){
        return slotManager.isSlotFileEmpty(index);
    }

    public void playShot(int line, int column){
        quartoModel.playShot(line,column);
    }

}
