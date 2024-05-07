package src.controller;

import src.model.QuartoModel;
import src.model.SlotManager;
import src.views.players.names.PlayersInformationsControl;

import java.io.IOException;
import java.util.Map;

public class PlayersInformations implements PlayersInformationsControl {
    QuartoModel quartoModel;
    private SlotManager slotManager;

    public PlayersInformations(){
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

    public void loadGame(String filename){
        quartoModel.chargeGame(filename);
    }

}
