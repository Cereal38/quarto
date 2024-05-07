package src.controller;

import src.model.QuartoModel;
import src.model.SlotManager;
import src.views.LoadSaveControl;

import java.io.IOException;
import java.util.Map;

/*
* TODO :
*   1. Function that tells us if the file is empty
*   2. Function that changes the file name takes in arguments player name 1 and player name 2
*   3.

* */


public class LoadSaveController implements LoadSaveControl {

    private SlotManager slotManager;
    private QuartoModel quartoModel;

    public LoadSaveController() {
        this.slotManager = new SlotManager();
        this.slotManager.loadFromDirectory();
        quartoModel = new QuartoModel();
    }

    // Method to retrieve slot names from the model
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
