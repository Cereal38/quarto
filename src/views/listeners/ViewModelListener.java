package src.views.listeners;

import src.model.game.QuartoModel;
import src.structures.SlotFile;

import java.io.IOException;
import java.util.List;

/**
 * An interface for objects that listen for changes in the ViewModel and need to update accordingly.
 */
public interface ViewModelListener {
    /**
     * Creates a new QuartoModel with the specified parameters.
     * @param type1 The type of player 1.
     * @param type2 The type of player 2.
     * @param name1 The name of player 1.
     * @param name2 The name of player 2.
     */
    void createModel(int type1, int type2, String name1, String name2);

    /**
     * Retrieves the current QuartoModel.
     * @return The current QuartoModel.
     */
    QuartoModel getModel();

    /**
     * Saves the game state to a file with the specified file name.
     * @param fileName The name of the file to save the game state to.
     * @throws IOException if an I/O error occurs while saving the game.
     */
    void saveGame(String fileName) throws IOException;

    /**
     * Loads a game state from the specified index.
     * @param index The index of the game state to load.
     */
    void loadGame(int index);

    /**
     * Retrieves the list of slot files.
     * @return The list of slot files.
     */
    List<SlotFile> getSlotFiles();

    /**
     * Clears the slot with the specified ID.
     * @param id The ID of the slot to clear.
     */
    void clearSlot(int id);
}
