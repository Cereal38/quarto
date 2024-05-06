package src.model;

import java.util.Scanner;

public class QuartoGameTester {
    private QuartoModel quartoModel;
    private Scanner scanner;

    public QuartoGameTester(int firstPlayerType, int secondPlayerType) {
        quartoModel = new QuartoModel(firstPlayerType, secondPlayerType);
        scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("Quarto Game Tester");
        System.out.println("Type 'help' for commands.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("joueur")) {
                System.out.println("Current player: " + quartoModel.getCurrentPlayer());
            } else if (input.equalsIgnoreCase("printT")) {
                printTable();
            } else if (input.equalsIgnoreCase("printP")) {
                printPawnAvailable();
            } else if (input.toLowerCase().startsWith("select ")) {
                try {
                    int pawnIndex = Integer.parseInt(input.substring(7).trim());
                    quartoModel.selectPawn(pawnIndex);
                    System.out.println("Pawn " + pawnIndex + " selected.");
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid pawn index.");
                }
            } else if (input.toLowerCase().startsWith("joue ")) {
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
            } else if (input.equalsIgnoreCase("undo")) {
                quartoModel.undo();
                System.out.println("Undo performed.");
            } else if (input.equalsIgnoreCase("redo")) {
                quartoModel.redo();
                System.out.println("Redo performed.");
            } else if (input.toLowerCase().startsWith("gagne ")) {
                try {
                    String[] coordinates = input.substring(6).trim().split("\\s+");
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
            } else if (input.equalsIgnoreCase("quit")) {
                System.out.println("Quitting game.");
                break;
            } else if (input.equalsIgnoreCase("help")) {
                printHelp();
            } else {
                System.out.println("Unknown command. Type 'help' for commands.");
            }
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
        System.out.println("joueur - Display current player.");
        System.out.println("printT - Display the table.");
        System.out.println("printP - Display the available pawns.");
        System.out.println("select i - Select pawn i to play.");
        System.out.println("joue i j - Play at coordinates i j.");
        System.out.println("undo - Undo last action.");
        System.out.println("redo - Redo last undone action.");
        System.out.println("gagne i j - Check win situation at coordinates i j.");
        System.out.println("quit - Quit the game.");
        System.out.println("help - Display available commands.");
    }

    public static void main(String[] args) {
        QuartoGameTester gameTester = new QuartoGameTester(0, 0); // Change player types as needed
        gameTester.startGame();
    }
}

