package src.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SlotManager {
    private Map<String, Long> slotFileDates;
    private static final String SLOTS_DIRECTORY = "slots";

    public SlotManager() {
        this.slotFileDates = new HashMap<>();
    }

    public boolean isSlotFileEmpty(int index){
        if(index < 0 || index >= getSlotFileDates().size()){
            throw new IllegalArgumentException("Invalid index " + index);
        }
        String fileName = getSlotFileDates().keySet().toArray(new String[0])[index];
        String filePath = SLOTS_DIRECTORY + File.separator + fileName;
        File file = new File(filePath);
        return file.length() == 0;
    }

    // Method to load slot data from all text files within the directory
    public void loadFromDirectory() {
        File directory = new File(SLOTS_DIRECTORY);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Slots directory not found.");
            return;
        }

        // Clear existing data before loading new data
        slotFileDates.clear();

        // Recursively traverse the directory to find all files
        traverseDirectory(directory);
    }

    // Recursive method to traverse directory and collect file information
    private void traverseDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files == null) {
            System.err.println("Failed to list files in the directory.");
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // Recursive call if it's a directory
                traverseDirectory(file);
            } else if (file.isFile() && file.getName().endsWith(".txt")) {
                // Process the file if it's a .txt file
                String fileName = file.getName();
                long lastModified = file.lastModified();
                slotFileDates.put(fileName, lastModified);

                // Debug print statement
                //System.out.println("Added file: " + fileName + ", Last modified: " + lastModified);
            }
        }
    }

    // Method to get the map of slot file names and their last modified dates
    public Map<String, Long> getSlotFileDates() {
        return this.slotFileDates;
    }

    public static void main(String[] args) {
        // Create a new instance of SlotManager
        SlotManager slotManager = new SlotManager();

        // Load slot data from the directory
        slotManager.loadFromDirectory();

        // Get the map of slot file names and their last modified dates
        Map<String, Long> slotFileDates = slotManager.getSlotFileDates();

        // Print out the collected data
        System.out.println("Slot files and their last modified dates:");
        for (Map.Entry<String, Long> entry : slotFileDates.entrySet()) {
            System.out.println("File: " + entry.getKey() + ", Last Modified: " + entry.getValue());
        }

        //Check if the slots are empty or not
        System.out.println("\nSlot file emptiness:");
        for(int i = 0; i < slotFileDates.size(); i++){
            boolean isEmpty = slotManager.isSlotFileEmpty(i);
            System.out.println("File " + i + " is empty: " + isEmpty);
        }
    }
}