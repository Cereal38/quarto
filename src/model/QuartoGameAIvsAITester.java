package src.model;

import java.io.IOException;
import java.util.Scanner;

public class QuartoGameAIvsAITester {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quarto Game AI vs AI Test");

        // Demander le type d'IA pour le premier joueur
        System.out.println("Choisissez le type d'IA pour le joueur 1 (1 - Random, 2 - Easy, 3 - Minimax): ");
        int player1Type = scanner.nextInt();

        // Demander le type d'IA pour le deuxième joueur
        System.out.println("Choisissez le type d'IA pour le joueur 2 (1 - Random, 2 - Easy, 3 - Minimax): ");
        int player2Type = scanner.nextInt();

        // Demander le nombre de parties à jouer
        System.out.println("Entrez le nombre de parties à jouer: ");
        int numberOfGames = scanner.nextInt();

        int ai1Wins = 0;
        int ai2Wins = 0;
        int draws = 0;
        int totalMoves = 0;

        for (int i = 0; i < numberOfGames; i++) {
            QuartoModel quartoModel = new QuartoModel(player1Type, player2Type, "AI1", "AI2");

            while (!quartoModel.isGameOver()) {
                if (quartoModel.getSelectedPawn() == null) {
                    quartoModel.selectPawn(0); // L'IA sélectionne un pion
                } else {
                    quartoModel.playShot(0, 0); // L'IA joue un coup
                    totalMoves++;
                }
            }

            if (quartoModel.isGameOver() && !quartoModel.isPawnListEmpty()) {
                if (quartoModel.getCurrentPlayer() == 1) {
                    ai2Wins++;
                } else {
                    ai1Wins++;
                }
            } else {
                draws++;
            }
        }

        System.out.println("Nombre de parties jouées : " + numberOfGames);
        System.out.println("IA 1 a gagné : " + ai1Wins + " parties.");
        System.out.println("IA 2 a gagné : " + ai2Wins + " parties.");
        System.out.println("Parties nulles : " + draws);
        System.out.println("Nombre moyen de coups par partie : " + (totalMoves / (double)numberOfGames));
    }
}
