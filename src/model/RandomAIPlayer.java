package src.model

import java.util.Random;

public class RandomAIPlayer implements Player {
    private Random random;

    public RandomAIPlayer() {
        random = new Random();
    }

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

    @Override
    public void playShot(QuartoModel quartoModel) {
        int line, column;
        do {
            line = random.nextInt(4);
            column = random.nextInt(4);
        } while (!quartoModel.isTableEmpty(line, column));//not efficient !
        quartoModel.playShotHuman(line, column);
    }
}
