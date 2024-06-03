package src.model.game;

import src.structures.SlotFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlotManager {
    private List<SlotFile> slotFiles;
    public static final String SLOTS_DIRECTORY = "slots";

    public SlotManager() {
        this.slotFiles = new ArrayList<>();
    }

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


    public void loadFromDirectory() {
        File directory = new File(SLOTS_DIRECTORY);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Slots directory not found.");
            return;
        }

        slotFiles.clear();
        traverseDirectory(directory);
    }

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


    public List<SlotFile> getSlotFiles() {
        loadFromDirectory();
        return slotFiles;
    }

    private SlotFile findSlotFileById(int id) {
        return slotFiles.stream().filter(slotFile -> slotFile.getId() == id).findFirst().orElse(null);
    }

    public void createNewFile(String fileName) throws IOException {
        File file = new File(SLOTS_DIRECTORY + File.separator + fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // Ensure the directory exists
            if (!file.createNewFile()) {
                throw new IOException("Failed to create new file: " + fileName);
            }
        }
    }
}