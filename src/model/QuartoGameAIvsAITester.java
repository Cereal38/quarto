package src.model;

import java.io.IOException;
import java.util.Scanner;

public class QuartoGameAIvsAITester {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quarto Game AI vs AI Test");

        // Ask for the AI type for player 1
        System.out.println("Choose AI type for player 1 (1 - Random, 2 - Easy, 3 - Medium, 4 - Minimax): ");
        int player1Type = scanner.nextInt();
        Heuristics heuristic1 = new Heuristics();
        if (player1Type == 4) {
            inputHeuristics(scanner, "1", heuristic1);
        }

        // Ask for the AI type for player 2
        System.out.println("Choose AI type for player 2 (1 - Random, 2 - Easy, 3 - Medium, 4 - Minimax): ");
        int player2Type = scanner.nextInt();
        Heuristics heuristic2 = new Heuristics();
        if (player2Type == 4) {
            inputHeuristics(scanner, "2", heuristic2);
        }

        // Ask for the number of games to play
        System.out.println("Enter the number of games to play: ");
        int numberOfGames = scanner.nextInt();

        int ai1Wins = 0;
        int ai2Wins = 0;
        int draws = 0;
        int totalMoves = 0;

        long totalPlayer1Time = 0;
        long totalPlayer2Time = 0;
        int totalPlayer1Moves = 0;
        int totalPlayer2Moves = 0;

        // Loop to play the specified number of games
        for (int i = 0; i < numberOfGames; i++) {
            QuartoModel quartoModel = new QuartoModel(player1Type, player2Type, "AI1", "AI2", heuristic1, heuristic2);

            // Play the game until there is a winner or a tie
            while (!quartoModel.hasAWinner() && !quartoModel.isATie()) {
                if (quartoModel.getSelectedPawn() == null) {
                    long startTime = System.nanoTime();
                    quartoModel.selectPawn(0); // AI selects a pawn
                    long endTime = System.nanoTime();

                    if (quartoModel.getCurrentPlayer() == 1) {
                        totalPlayer1Time += (endTime - startTime);
                        totalPlayer1Moves++;
                    } else {
                        totalPlayer2Time += (endTime - startTime);
                        totalPlayer2Moves++;
                    }

                } else {
                    long startTime = System.nanoTime();
                    quartoModel.playShot(0, 0); // AI plays a shot
                    long endTime = System.nanoTime();

                    if (quartoModel.getCurrentPlayer() == 1) {
                        totalPlayer1Time += (endTime - startTime);
                        totalPlayer1Moves++;
                    } else {
                        totalPlayer2Time += (endTime - startTime);
                        totalPlayer2Moves++;
                    }

                    totalMoves++;
                }
            }

            // Update the win counters
            if (quartoModel.hasAWinner() && !quartoModel.isPawnListEmpty()) {
                if (quartoModel.getCurrentPlayer() == 1) {
                    ai1Wins++;
                } else {
                    ai2Wins++;
                }
            } else {
                draws++;
            }
        }

        // Display the results
        System.out.println("Number of games played: " + numberOfGames);
        System.out.println("AI 1 won: " + ai1Wins + " games.");
        System.out.println("AI 2 won: " + ai2Wins + " games.");
        System.out.println("Draws: " + draws);
        System.out.println("Average number of moves per game: " + (totalMoves / (double) numberOfGames));

        // Display heuristics if the players are Minimax
        if (player1Type == 4) {
            displayHeuristics(heuristic1, "AI1");
        }
        if (player2Type == 4) {
            displayHeuristics(heuristic2, "AI2");
        }

        // Calculate and display the average execution time for each player
        if (totalPlayer1Moves > 0) {
            double avgPlayer1Time = totalPlayer1Time / (double) totalPlayer1Moves / 1_000_000.0; // Convert to milliseconds
            System.out.println("Average execution time for AI1: " + avgPlayer1Time + " ms");
        }

        if (totalPlayer2Moves > 0) {
            double avgPlayer2Time = totalPlayer2Time / (double) totalPlayer2Moves / 1_000_000.0; // Convert to milliseconds
            System.out.println("Average execution time for AI2: " + avgPlayer2Time + " ms");
        }

        scanner.close();
    }

    private static void inputHeuristics(Scanner scanner, String playerNumber, Heuristics heuristic) {
        System.out.println("Enter heuristic values for player " + playerNumber + ": ");

        System.out.print("Win state value (winState): ");
        int winStateValue = scanner.nextInt();
        heuristic.setWinStateValue(winStateValue);

        System.out.print("Same character value (sameChar): ");
        int sameCharValue = scanner.nextInt();
        heuristic.setSameCharValue(sameCharValue);

        System.out.print("Line of three value (lineOfThree): ");
        int lineOfThreeValue = scanner.nextInt();
        heuristic.setLineOfThreeValue(lineOfThreeValue);

        System.out.print("Common character value (commonChar): ");
        int commonCharValue = scanner.nextInt();
        heuristic.setCommonCharValue(commonCharValue);

        System.out.println("Risk value (riskValue): ");
        int riskValue = scanner.nextInt();
        heuristic.setRiskValue(riskValue);
    }

    private static void displayHeuristics(Heuristics heuristics, String playerName) {
        System.out.println("Heuristics used by " + playerName + ":");
        System.out.println("Win state value: " + heuristics.getWinStateValue());
        System.out.println("Same character value: " + heuristics.getSameCharValue());
        System.out.println("Line of three value: " + heuristics.getLineOfThreeValue());
        System.out.println("Common character value: " + heuristics.getCommonCharValue());
        System.out.println("Risk value: " + heuristics.getRiskValue());
    }
}