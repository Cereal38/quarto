package src.model.ai;

import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An AI player that selects moves randomly in the Quarto game.
 */

public class RandomAIPlayer implements Player {
    private Random random;

    /**
     * Constructs a RandomAIPlayer object.
     */
    public RandomAIPlayer() {
        random = new Random();
    }

    /**
     * Selects a pawn randomly to play in the Quarto game.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    @Override
    public void selectPawn(QuartoModel quartoModel) {
        QuartoPawn[] pawnAvailable = quartoModel.getPawnAvailable();
        int availableCount = 0;
        for (QuartoPawn pawn : pawnAvailable) {
            if (pawn != null) {
                availableCount++;
            }
        }
        int selectedIndex = random.nextInt(availableCount);
        int count = 0;
        for (int i = 0; i < pawnAvailable.length; i++) {
            if (pawnAvailable[i] != null) {
                if (count == selectedIndex) {
                    quartoModel.selectPawnHuman(i);
                    break;
                }
                count++;
            }
        }
    }

    /**
     * Plays a shot randomly in the Quarto game.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    @Override
    public void playShot(QuartoModel quartoModel) {
        List<int[]> emptyCells = new ArrayList<>();

        // Find all the empty cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            // Select one random cell in all the available cells
            int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            int line = randomCell[0];
            int column = randomCell[1];

            // Play shot on the random cell
            quartoModel.playShotHuman(line, column);
        } else {
            // All cells are used
            System.out.println("All cells are occupied. Impossible to play.");
        }
    }
}
