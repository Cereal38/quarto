package src.model;

import java.util.ArrayList;
import java.util.List;
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
    List<int[]> emptyCells = new ArrayList<>();

    // Find all the empty cells
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (quartoModel.isTableEmpty(i, j)) {
          emptyCells.add(new int[] { i, j });
        }
      }
    }

    if (!emptyCells.isEmpty()) {
      // Select one random cell in all the available cells
      int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
      int line = randomCell[0];
      int column = randomCell[1];

      // play shot on the random cell
      quartoModel.playShotHuman(line, column);
    } else {
      // All cells are used
      System.out.println("All cells are occupied. Impossible to play.");
    }
  }

}
