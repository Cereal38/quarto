package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAIPlayer implements Player{

    private final Random random;
    private final QuartoWin quartoWin;
    public EasyAIPlayer() {
        random = new Random();
        quartoWin = new QuartoWin();
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

    public boolean hasPotentialList(QuartoPawn[] pawns){
        //if list is complete, there is no potential
        if(isCompleteList(pawns)){
            return false;
        }
        //Initialize pawn count and characteristics counts
        int pawnCount = 4;
        int roundCount = 0;
        int whiteCount = 0;
        int littleCount = 0;
        int hollowCount = 0;

        for(QuartoPawn pawn : pawns){
            if(pawn == null){
                pawnCount--;
                continue;
            }
            roundCount += pawn.isRound() ? 1 : 0;
            whiteCount += pawn.isWhite() ? 1 : 0;
            littleCount += pawn.isLittle() ? 1 : 0;
            hollowCount += pawn.isHollow() ? 1 : 0;
        }

        //returns true if all pawns in the list share at least one characteristic
        return (roundCount == 0 || roundCount == pawnCount ||
                whiteCount == 0 || whiteCount == pawnCount ||
                littleCount == 0 || littleCount == pawnCount ||
                hollowCount == 0 || hollowCount == pawnCount);
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
            System.arraycopy(grid[i], 0, row, 0, 4);
            if (!isCompleteList(row) && hasPotentialList(row)) {
                updateCharacteristics(characteristics, row);
            }
        }

        // for each column
        for (int j = 0; j < 4; j++) {
            QuartoPawn[] column = new QuartoPawn[4];
            for (int i = 0; i < 4; i++) {
                column[i] = grid[i][j];
            }
            if (!isCompleteList(column) && hasPotentialList(column)) {
                updateCharacteristics(characteristics, column);
            }
        }

        // first diagonal (up left to down right)
        QuartoPawn[] diagonal1 = new QuartoPawn[4];
        for (int i = 0; i < 4; i++) {
            diagonal1[i] = grid[i][i];
        }
        if (!isCompleteList(diagonal1) && hasPotentialList(diagonal1)) {
            updateCharacteristics(characteristics, diagonal1);
        }

        // second diagonal (up right to down left)
        QuartoPawn[] diagonal2 = new QuartoPawn[4];
        for (int i = 0; i < 4; i++) {
            diagonal2[i] = grid[i][3 - i];
        }
        if (!isCompleteList(diagonal2) && hasPotentialList(diagonal2)) {
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
    public void selectPawn(QuartoModel quartoModel) {
        int[] pawnScores = calculateAvailablePawnsScores(quartoModel);
        int minScore = Integer.MAX_VALUE;
        List<Integer> minScoreIndices = new ArrayList<>();

        // Find the indices of the pawns with the smallest score
        for (int i = 0; i < pawnScores.length; i++) {
            if (pawnScores[i] < minScore) {
                minScore = pawnScores[i];
                minScoreIndices.clear();
                minScoreIndices.add(i);
            } else if (pawnScores[i] == minScore) {
                minScoreIndices.add(i);
            }
        }

        // Select one random index among those with the minimum score
        int selectedPawnIndex = minScoreIndices.get(random.nextInt(minScoreIndices.size()));
        quartoModel.selectPawnHuman(selectedPawnIndex);
    }

    @Override
    public void playShot(QuartoModel quartoModel) {
        List<int[]> emptyCells = new ArrayList<>();
        QuartoPawn[][] grid = quartoModel.getTable();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            QuartoPawn selectedPawn = quartoModel.getSelectedPawn();

            // Check for winning move
            for (int[] cell : emptyCells) {
                if (isWinningMove(grid, selectedPawn, cell[0], cell[1])) {
                    quartoModel.playShotHuman(cell[0], cell[1]);
                    System.out.println("Winning shot played by AI at (" + cell[0] + ", " + cell[1] + ").");
                    return;
                }
            }

            // Check for blocking move
            QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
            for (int[] cell : emptyCells) {
                for (QuartoPawn pawn : availablePawns) {
                    if (pawn != null && isWinningMove(grid, pawn, cell[0], cell[1])) {
                        quartoModel.playShotHuman(cell[0], cell[1]);
                        System.out.println("Blocking shot played by AI at (" + cell[0] + ", " + cell[1] + ").");
                        return;
                    }
                }
            }

            // Play optimal move
            int maxScore = Integer.MIN_VALUE;
            List<int[]> bestCells = new ArrayList<>();
            for (int[] cell : emptyCells) {
                int score = evaluatePosition(grid, selectedPawn, cell[0], cell[1]);
                if (score > maxScore) {
                    maxScore = score;
                    bestCells.clear();
                    bestCells.add(cell);
                } else if (score == maxScore) {
                    bestCells.add(cell);
                }
            }

            if (!bestCells.isEmpty()) {
                int[] bestCell = bestCells.get(random.nextInt(bestCells.size()));
                quartoModel.playShotHuman(bestCell[0], bestCell[1]);
                System.out.println("Optimal shot played by AI at (" + bestCell[0] + ", " + bestCell[1] + ").");
            }
        } else {
            System.out.println("All cells are occupied. Impossible to play.");
        }
    }

    // Checks if placing a pawn at (x, y) results in a winning move
    private boolean isWinningMove(QuartoPawn[][] grid, QuartoPawn pawn, int x, int y) {
        grid[x][y] = pawn;
        boolean winning = quartoWin.winSituationLine(grid, x) ||
                quartoWin.winSituationColumn(grid, y) ||
                quartoWin.winSituationDiagonal(grid, x, y);
        grid[x][y] = null;
        return winning;
    }

    // Evaluates the optimal position to play for the selected pawn
    private int evaluatePosition(QuartoPawn[][] grid, QuartoPawn pawn, int x, int y) {
        int score = 0;
        grid[x][y] = pawn;
        score += evaluateLine(grid, x, 0, x, 3); // Row
        score += evaluateLine(grid, 0, y, 3, y); // Column
        if (x == y) {
            score += evaluateLine(grid, 0, 0, 3, 3); // Main diagonal
        }
        if (x + y == 3) {
            score += evaluateLine(grid, 0, 3, 3, 0); // Anti-diagonal
        }
        grid[x][y] = null;
        return score;
    }

    // Evaluates a line (row, column, or diagonal) for potential score
    private int evaluateLine(QuartoPawn[][] grid, int x1, int y1, int x2, int y2) {
        int[] characteristics = new int[8];
        int dx = (x2 - x1) / 3;
        int dy = (y2 - y1) / 3;
        QuartoPawn[] line = new QuartoPawn[4];

        for (int i = 0; i < 4; i++) {
            line[i] = grid[x1 + i * dx][y1 + i * dy];
        }

        updateCharacteristics(characteristics, line);

        int score = 0;
        for (int count : characteristics) {
            if (count == 3) {
                score++;
            }
        }
        return score;
    }
}
