package src.model;

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

    public void renameSlotFile(int id, String newFileName) {
        SlotFile slotFile = findSlotFileById(id);
        if (slotFile == null) {
            throw new IllegalArgumentException("Invalid id " + id);
        }

        String oldFileName = slotFile.getFilename();
        String oldFilePath = SLOTS_DIRECTORY + File.separator + oldFileName;
        String newFilePath = SLOTS_DIRECTORY + File.separator + newFileName;

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        if (oldFile.renameTo(newFile)) {
            slotFile.setFilename(newFileName);
            slotFile.setLastModified(newFile.lastModified());
            System.out.println("File renamed successfully.");
        } else {
            System.err.println("Failed to rename file.");
        }
    }


    public boolean isSlotFileEmpty(int id) {
        SlotFile slotFile = findSlotFileById(id);
        if (slotFile == null) {
            throw new IllegalArgumentException("Invalid id " + id);
        }

        String filePath = SLOTS_DIRECTORY + File.separator + slotFile.getFilename();
        File file = new File(filePath);
        return file.length() == 0;
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


    public void renameSlotFile(int id, String playerName1, String playerName2) {
        String newFileName = playerName1 + "_vs_" + playerName2 + ".txt";
        renameSlotFile(id, newFileName);
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
//  public static void main(String[] args) {
//    SlotManager slotManager = new SlotManager();
//    slotManager.loadFromDirectory();
//
//    System.out.println("Slot Files:");
//
//    slotManager.renameSlotFile(2, "adam", "rymav2");
//    //slotManager.clearSlotFile(0);
//    for (SlotFile slotFile : slotManager.getSlotFiles()) {
//      System.out.println("ID: " + slotFile.getId() + ", Filename: " + slotFile.getFilename() + ", Last Modified: " + slotFile.getLastModified());
//    }
//  }

}