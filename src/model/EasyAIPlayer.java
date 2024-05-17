package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAIPlayer implements Player{

    private Random random;
    public EasyAIPlayer() {
        random = new Random();
    }

    public boolean isCompleteList(QuartoPawn[] pawns) {
        if (pawns.length != 4) {
            throw new IllegalArgumentException("The list must contain exactly 4 QuartoPawn objects.");
        }
        for (QuartoPawn pawn : pawns) {
            if (pawn == null) {
                return false;
            }
        }
        return true;
    }

    public void updateCharacteristics(int[] characteristics, QuartoPawn[] pawns) {
        // Checks if input tables are of correct size
        if (characteristics.length != 8 || pawns.length != 4) {
            throw new IllegalArgumentException("Invalid input array size.");
        }

        // for each pawn
        for (QuartoPawn pawn : pawns) {
            // null is ignored
            if (pawn == null) {
                continue;
            }
            // Checks characteristics
            if (pawn.isRound()) {
                characteristics[1]++; // round
            } else {
                characteristics[0]++; // square
            }
            if (pawn.isWhite()) {
                characteristics[3]++; // white
            } else {
                characteristics[2]++; // black
            }
            if (pawn.isLittle()) {
                characteristics[5]++; // little
            } else {
                characteristics[4]++; // big
            }
            if (pawn.isHollow()) {
                characteristics[7]++; // hollow
            } else {
                characteristics[6]++; // full
            }
        }
    }

    public int[] calculateCharacteristicsFromGrid(QuartoPawn[][] grid) {
        int[] characteristics = new int[8];

        // for each row
        for (int i = 0; i < 4; i++) {
            QuartoPawn[] row = new QuartoPawn[4];
            for (int j = 0; j < 4; j++) {
                row[j] = grid[i][j];
            }
            if (!isCompleteList(row)) {
                updateCharacteristics(characteristics, row);
            }
        }

        // for each column
        for (int j = 0; j < 4; j++) {
            QuartoPawn[] column = new QuartoPawn[4];
            for (int i = 0; i < 4; i++) {
                column[i] = grid[i][j];
            }
            if (!isCompleteList(column)) {
                updateCharacteristics(characteristics, column);
            }
        }

        // first diagonal (up left to down right)
        QuartoPawn[] diagonal1 = new QuartoPawn[4];
        for (int i = 0; i < 4; i++) {
            diagonal1[i] = grid[i][i];
        }
        if (!isCompleteList(diagonal1)) {
            updateCharacteristics(characteristics, diagonal1);
        }

        // second diagonal (up right to down left)
        QuartoPawn[] diagonal2 = new QuartoPawn[4];
        for (int i = 0; i < 4; i++) {
            diagonal2[i] = grid[i][3 - i];
        }
        if (!isCompleteList(diagonal2)) {
            updateCharacteristics(characteristics, diagonal2);
        }

        return characteristics;
    }

    public int[] calculateAvailablePawnsScores(QuartoModel quartoModel) {
        QuartoPawn[] listOfPawn = quartoModel.getPawnAvailable();
        int[] pawnScores = new int[16];

        // Get the characteristics from the current grid
        int[] currentCharacteristics = calculateCharacteristicsFromGrid(quartoModel.getTable());

        // Iterate over each available pawn
        for (int i = 0; i < 16; i++) {
            QuartoPawn pawn = listOfPawn[i];
            int score = 0;
            if(pawn == null){
                score = Integer.MAX_VALUE;
            }
            else {
                // Calculate the score for the current pawn based on its characteristics
                if (pawn.isRound()) {
                    score += currentCharacteristics[1];
                } else {
                    score += currentCharacteristics[0];
                }

                if (pawn.isWhite()) {
                    score += currentCharacteristics[3];
                } else {
                    score += currentCharacteristics[2];
                }

                if (pawn.isLittle()) {
                    score += currentCharacteristics[5];
                } else {
                    score += currentCharacteristics[4];
                }

                if (pawn.isHollow()) {
                    score += currentCharacteristics[7];
                } else {
                    score += currentCharacteristics[6];
                }
            }

            // Assign the calculated score to the corresponding position in the pawnScores array
            pawnScores[i] = score;
        }

        return pawnScores;
    }

    //if several pawns have the same score, takes the first found and not a random one
    @Override
    public void selectPawn(QuartoModel quartoModel){
        int[] pawnScores = calculateAvailablePawnsScores(quartoModel);
        int minScore = Integer.MAX_VALUE;
        int selectedPawnIndex = -1;

        // Find the index of the pawn with the smallest score
        for (int i = 0; i < pawnScores.length; i++) {
            if (pawnScores[i] < minScore) {
                minScore = pawnScores[i];
                selectedPawnIndex = i;
            }
        }
        quartoModel.selectPawnHuman(selectedPawnIndex);
    }

    //RANDOM
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

            // play shot on the random cell
            quartoModel.playShotHuman(line, column);
            System.out.println("Shot played by AI at (" + line + ", " + column + ").");
        } else {
            // All cells are used
            System.out.println("All cells are occupied. Impossible to play.");
        }
    }

}
