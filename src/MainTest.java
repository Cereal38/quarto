//package src;
//
//import java.util.Date;
//import java.util.Map;
//
//public class MainTest {
//
//    public static void main(String[] args) {
//        LoadSaveController loadSaveController = new LoadSaveController();
//
//        // Retrieve slot file dates using LoadSaveController
//        Map<String, Long> slotFileDates = loadSaveController.getSlotFileDates();
//
//        // Print slot file names and last modified dates
//        System.out.println("Slot File Dates:");
//        for (Map.Entry<String, Long> entry : slotFileDates.entrySet()) {
//            System.out.println("Slot Name: " + entry.getKey() + ", Last Modified Date: " + new Date(entry.getValue()));
//        }
//    }
//}
