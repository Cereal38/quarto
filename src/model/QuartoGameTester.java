package src.model;

import java.io.IOException;
import java.util.Scanner;

public class QuartoGameTester {
    private QuartoModel quartoModel;
    private Scanner scanner;
    private SlotManager manager;

    public QuartoGameTester() {
        scanner = new Scanner(System.in);
    }

    public void startGame() throws IOException {
        manager = new SlotManager();
        System.out.println("Quarto Game Tester");
        System.out.println("New game ? 0 - yes, 1 - no");
        int game = scanner.nextInt();
        if (game == 0) {
            System.out.println("Choose player types:");
            System.out.println("0 - Human, 1 - Random AI, 2 - Easy AI, 3- MinMax AI");

            System.out.print("Player 1 type: ");
            int firstPlayerType = scanner.nextInt();
            System.out.print("Player 2 type: ");
            int secondPlayerType = scanner.nextInt();

            scanner.nextLine();//skip the \n

            quartoModel = new QuartoModel(firstPlayerType, secondPlayerType, "J1", "J2");

        } else {
            manager = new SlotManager();
            manager.loadFromDirectory();
            System.out.println("Choose an index 0 and " + manager.getSlotFileDates().size());
            int index = scanner.nextInt();
            while (index < 0 || index > manager.getSlotFileDates().size()) {
                System.out.println("Choose an index 0 and " + manager.getSlotFileDates().size());
                index = scanner.nextInt();
            }
            quartoModel = new QuartoModel(index);
        }
        System.out.println("Type 'help' for commands.");

        while (true) {
            System.out.println(" Win situation : " + quartoModel.isGameOver());
            System.out.println(" Table state : ");
            printTable();
            System.out.println(" Pawn list : " );
            printPawnAvailable();
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("player")) {
                System.out.println("Current player: " + quartoModel.getCurrentPlayer());
            } else if (input.equalsIgnoreCase("printT")) {
                printTable();
            } else if (input.equalsIgnoreCase("printP")) {
                printPawnAvailable();
            } else if (input.toLowerCase().startsWith("select ")) {
                if(quartoModel.getCurrentPlayerType() != 0 ){
                    System.out.println("Select option is for human players only");
                }
                else {
                    try {
                        int pawnIndex = Integer.parseInt(input.substring(7).trim());
                        quartoModel.selectPawn(pawnIndex);
                        System.out.println("Pawn " + quartoModel.getSelectedPawn().getPawn() + " selected.");
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Invalid pawn index.");
                    }
                }
            } else if (input.toLowerCase().startsWith("play ")) {
                if(quartoModel.getCurrentPlayerType() != 0 ){
                    System.out.println("Play option is for human players only");
                }
                else {
                    try {
                        String[] coordinates = input.substring(5).trim().split("\\s+");
                        if (coordinates.length == 2) {
                            int row = Integer.parseInt(coordinates[0]);
                            int column = Integer.parseInt(coordinates[1]);
                            quartoModel.playShot(row, column);
                            System.out.println("Shot played at (" + row + ", " + column + ").");
                        } else {
                            System.out.println("Invalid coordinates.");
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Invalid coordinates.");
                    }
                }
            }
            else if (input.equalsIgnoreCase("ai")){
                if((quartoModel.getCurrentPlayerType() == 0 )){
                    System.out.println("The player is not an AI");
                }
                else{
                    if (quartoModel.getSelectedPawn() == null){
                        quartoModel.selectPawn(0);
                        System.out.println("Pawn " + quartoModel.getSelectedPawn().getPawn() + " selected by the ai.");
                    }
                    else {
                        quartoModel.playShot(0, 0);
                        //if (!quartoModel.isPawnListEmpty()) {
                        //    quartoModel.selectPawn(0);
                        //    System.out.println("Pawn " + quartoModel.getSelectedPawn().getPawn() + " selected by the ai.");
                        //}
                    }
                }
            } else if (input.equalsIgnoreCase("undo")) {
                quartoModel.undo();
                System.out.println("Undo performed.");
            } else if (input.equalsIgnoreCase("redo")) {
                quartoModel.redo();
                System.out.println("Redo performed.");
            } else if (input.toLowerCase().startsWith("win ")) {
                try {
                    String[] coordinates = input.substring(4).trim().split("\\s+");
                    if (coordinates.length == 2) {
                        int row = Integer.parseInt(coordinates[0]);
                        int column = Integer.parseInt(coordinates[1]);
                        System.out.println("Win situation: " + quartoModel.winSituation(row, column));
                    } else {
                        System.out.println("Invalid coordinates.");
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid coordinates.");
                }
            } else if (input.toLowerCase().startsWith("charge ")) {
                int index = Integer.parseInt(input.substring(7).trim());
                quartoModel.chargeGame(index);
                System.out.println("Game loaded from file of index: " + index);
            } else if (input.toLowerCase().startsWith("save ")) {
                //                String fileName = input.substring(5).trim();
                manager.loadFromDirectory();
                System.out.println("Choose an index 0 and " + manager.getSlotFileDates().size());
                int index = scanner.nextInt();
                while (index < 0 || index > manager.getSlotFileDates().size()) {
                    System.out.println("Choose an index 0 and " + manager.getSlotFileDates().size());
                    index = scanner.nextInt();
                }
                quartoModel.saveFile(index);
                System.out.println("Game saved");
            } else if (input.equalsIgnoreCase("quit")) {
                System.out.println("Quitting game.");
                break;
            } else if (input.equalsIgnoreCase("help")) {
                printHelp();
            } else if (input.equalsIgnoreCase("currentState")) {
                QuartoHistory state = quartoModel.getCurrentState();
                printState(state);
            } else if (input.equalsIgnoreCase("headState")) {
                QuartoHistory head = quartoModel.getFirstState();
                while (head.getNext() != null) {
                    printState(head);
                    head = head.getNext();
                }
                printState(head);
            } else {
                System.out.println("Unknown command. Type 'help' for commands.");
            }
        }
    }

    private void printState(QuartoHistory state) {
        if (state.getState() == 0) {
            System.out.println("Etat 0: le joueur "+ state.currentPlayer + " " + state.playerName + " a donné le pion" + state.getIndexPawn());
        } else if (state.getState() == 1) {
            System.out.println("Etat 1: le joueur "+ state.currentPlayer + " " + state.playerName + " a posé le pion en [" + state.getLine() + "," + state.getColumn()+"]");
        } else {
            System.out.println("Début du jeu !");
        }
    }

    private void printTable() {
        QuartoPawn[][] table = quartoModel.getTable();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                QuartoPawn pawn = table[i][j];
                if (pawn == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(pawn.getPawn() + " ");
                }
            }
            System.out.println();
        }
    }

    private void printPawnAvailable() {
        QuartoPawn[] pawnAvailable = quartoModel.getPawnAvailable();
        System.out.print("Available Pawns: ");
        for (int i = 0; i < 16; i++) {
            if (pawnAvailable[i] != null) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("player - Display current player.");
        System.out.println("printT - Display the table.");
        System.out.println("printP - Display the available pawns.");
        System.out.println("select i - Select pawn i to play.");
        System.out.println("play i j - Play at coordinates i j.");
        System.out.println("ai - Allow the ai to play its turn.");
        System.out.println("undo - Undo last action.");
        System.out.println("redo - Redo last undone action.");
        System.out.println("win i j - Check win situation at coordinates i j.");
        System.out.println("charge filename - Load game from file.");
        System.out.println("save filename - Save game to file.");
        System.out.println("quit - Quit the game.");
        System.out.println("help - Display available commands.");
    }

    public static void main(String[] args) throws IOException {
        QuartoGameTester gameTester = new QuartoGameTester();
        gameTester.startGame();
    }
}
