package src.views;

import java.io.IOException;
import java.util.Map;

public interface LoadSaveControl {
    public Map<String, Long> getSlotFileDates();
    public void saveGame(String fileName) throws IOException;

    public void loadGame(String filename);
}
