/**
 * The SlotManager class manages the slot files used for saving and loading game states.
 * It provides methods to interact with the slot files, such as clearing a slot file, loading slot files
 * from the designated directory, getting the list of slot files, finding a slot file by ID,
 * and creating a new slot file.
 */
package src.model.game;

import src.structures.SlotFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlotManager {
    /**  List to store SlotFile objects representing saved game slots*/
    private List<SlotFile> slotFiles;

    /** Constant representing the directory where slot files are stored*/
    public final String SLOTS_DIRECTORY = "slots";


    /**
     * Constructs a new SlotManager object.
     * Initializes the list of slot files.
     */
    public SlotManager() {
        this.slotFiles = new ArrayList<>();
    }

    /**
     * Clears the slot file with the specified ID.
     *
     * @param id The ID of the slot file to clear.
     * @return The filename of the cleared slot file, or null if the file couldn't be deleted.
     */
    public String clearSlotFile(int id) {
        SlotFile slotFile = findSlotFileById(id);
        if (slotFile == null) {
            throw new IllegalArgumentException("Invalid id " + id);
        }

        String filePath = SLOTS_DIRECTORY + File.separator + slotFile.getFilename();

        // Create a File object representing the file to be deleted
        File fileToDelete = new File(filePath);

        // Check if the file exists before attempting to delete
        if (fileToDelete.exists()) {
            // Attempt to delete the file
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully: " + filePath);
            } else {
                System.err.println("Failed to delete file: " + filePath);
            }
        } else {
            System.err.println("File does not exist: " + filePath);
        }

        // Return the filename as confirmation (or null if file doesn't exist or couldn't be deleted)
        return fileToDelete.exists() ? slotFile.getFilename() : null;
    }

    /**
     * Loads slot files from the slots directory.
     */
    public void loadFromDirectory() {
        File directory = new File(SLOTS_DIRECTORY);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Slots directory not found.");
            return;
        }

        slotFiles.clear();
        traverseDirectory(directory);
    }

    /**
     * Traverses the given directory and adds slot files to the list.
     *
     * @param directory The directory to traverse.
     */
    private void traverseDirectory(File directory) {
        File[] files = directory.listFiles();
        int id = slotFiles.size(); // Initialize id based on the size of slotFiles list

        if (files == null) {
            System.err.println("Failed to list files in the directory.");
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                traverseDirectory(file);
            } else if (file.isFile()) {
                String fileName = file.getName();
                long lastModified = file.lastModified();
                slotFiles.add(new SlotFile(id++, fileName, lastModified));
            }
        }
    }

    /**
     * Gets the list of slot files.
     *
     * @return The list of slot files.
     */
    public List<SlotFile> getSlotFiles() {
        loadFromDirectory();
        return slotFiles;
    }

    /**
     * Finds a slot file in the list by its ID.
     *
     * @param id The ID of the slot file to find.
     * @return The SlotFile object with the specified ID, or null if not found.
     */
    private SlotFile findSlotFileById(int id) {
        return slotFiles.stream().filter(slotFile -> slotFile.getId() == id).findFirst().orElse(null);
    }

    /**
     * Creates a new file with the provided file name in the specified directory.
     * If a file with the same name already exists, it throws an IOException.
     *
     * @param fileName the name of the file to create
     * @throws IOException if an I/O error occurs or if a file with the same name already exists
     */
    public void createNewFile(String fileName) throws IOException {
        File file = new File(SLOTS_DIRECTORY + File.separator + fileName);
        if (file.exists()) {
            throw new IOException("File already exists: " + fileName);
        }
        file.getParentFile().mkdirs(); // Ensure the directory exists
        if (!file.createNewFile()) {
            throw new IOException("Failed to create new file: " + fileName);
        }
    }

}
