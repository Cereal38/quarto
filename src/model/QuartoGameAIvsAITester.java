package src.model;

import java.io.IOException;
import java.util.Scanner;

public class QuartoGameAIvsAITester {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quarto Game AI vs AI Test");

        // Demander le type d'IA pour le premier joueur
        System.out.println("Choisissez le type d'IA pour le joueur 1 (1 - Random, 2 - Easy, 3 - Medium, 4 - Minimax): ");
        int player1Type = scanner.nextInt();
        Heuristics heuristic1 = new Heuristics();
        if (player1Type == 4) {
            inputHeuristics(scanner, "1",heuristic1);
        }

        // Demander le type d'IA pour le deuxième joueur
        System.out.println("Choisissez le type d'IA pour le joueur 2 (1 - Random, 2 - Easy, 3 - Medium, 4 - Minimax): ");
        int player2Type = scanner.nextInt();
        Heuristics heuristic2 = new Heuristics();
        if (player2Type == 4) {
            inputHeuristics(scanner, "2",heuristic2);
        }

        // Demander le nombre de parties à jouer
        System.out.println("Entrez le nombre de parties à jouer: ");
        int numberOfGames = scanner.nextInt();

        int ai1Wins = 0;
        int ai2Wins = 0;
        int draws = 0;
        int totalMoves = 0;

        for (int i = 0; i < numberOfGames; i++) {
            QuartoModel quartoModel = new QuartoModel(player1Type, player2Type, "AI1", "AI2", heuristic1, heuristic2);

            while (!quartoModel.hasAWinner() && !quartoModel.isATie()) {
                if (quartoModel.getSelectedPawn() == null) {
                    quartoModel.selectPawn(0); // L'IA sélectionne un pion
                } else {
                    quartoModel.playShot(0, 0); // L'IA joue un coup
                    totalMoves++;
                }
            }

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

        System.out.println("Nombre de parties jouées : " + numberOfGames);
        System.out.println("IA 1 a gagné : " + ai1Wins + " parties.");
        System.out.println("IA 2 a gagné : " + ai2Wins + " parties.");
        System.out.println("Parties nulles : " + draws);
        System.out.println("Nombre moyen de coups par partie : " + (totalMoves / (double) numberOfGames));

        // Afficher les heuristiques utilisées si les joueurs sont de type Minimax
        if (player1Type == 4) {
            displayHeuristics(heuristic1, "AI1");
        }
        if (player2Type == 4) {
            displayHeuristics(heuristic2, "AI2");
        }

        scanner.close();
    }

    private static void inputHeuristics(Scanner scanner, String playerNumber, Heuristics heuristic) {
        System.out.println("Entrez les valeurs heuristiques pour le joueur " + playerNumber + ": ");

        System.out.print("Valeur de l'état de victoire (winState): ");
        int winStateValue = scanner.nextInt();
        heuristic.setWinStateValue(winStateValue);

        System.out.print("Valeur du même caractère (sameChar): ");
        int sameCharValue = scanner.nextInt();
        heuristic.setSameCharValue(sameCharValue);

        System.out.print("Valeur de la ligne de trois (lineOfThree): ");
        int lineOfThreeValue = scanner.nextInt();
        heuristic.setLineOfThreeValue(lineOfThreeValue);

        System.out.print("Valeur du caractère commun (commonChar): ");
        int commonCharValue = scanner.nextInt();
        heuristic.setCommonCharValue(commonCharValue);

    }

    private static void displayHeuristics(Heuristics heuristics, String playerName) {
        System.out.println("Heuristiques utilisées par " + playerName + ":");
        System.out.println("Valeur de l'état de victoire: " + heuristics.getWinStateValue());
        System.out.println("Valeur du même caractère: " + heuristics.getSameCharValue());
        System.out.println("Valeur de la ligne de trois: " + heuristics.getLineOfThreeValue());
        System.out.println("Valeur du caractère commun: " + heuristics.getCommonCharValue());
    }
}
