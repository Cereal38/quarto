package src.model.ai;

import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;
import src.model.game.QuartoWin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediumAIPlayer implements Player {

    private final Random random;
    private final QuartoWin quartoWin;
    public MediumAIPlayer() {
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

    // Checks if placing a pawn is an immediate winning pawn
    private boolean isWinningPawn(QuartoModel quartoModel, QuartoPawn pawn) {
        List<int[]> emptyCells = new ArrayList<>();
        QuartoPawn[][] grid = quartoModel.getTable();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        for (int[] cell : emptyCells) {
            if (isWinningMove(grid, pawn, cell[0], cell[1])) {
                return true;
            }
        }
        return false;
    }

    private boolean areAllPawnWinning(QuartoModel quartoModel){
        QuartoPawn[] listOfPawn = quartoModel.getPawnAvailable();
        for(QuartoPawn pawn : listOfPawn){
            if(pawn != null && !isWinningPawn(quartoModel, pawn)){
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
            characteristics[pawn.isRound()? 1 : 0]++; // round or square
            characteristics[pawn.isWhite()? 3 : 2]++; // white or black
            characteristics[pawn.isLittle()? 5 : 4]++; // little or big
            characteristics[pawn.isHollow()? 7 : 6]++; // hollow or full
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
                score += currentCharacteristics[pawn.isRound()? 1 : 0];
                score += currentCharacteristics[pawn.isWhite()? 3 : 2];
                score += currentCharacteristics[pawn.isLittle()? 5 : 4];
                score += currentCharacteristics[pawn.isHollow()? 7 : 6];

                if(isWinningPawn(quartoModel, pawn)){
                    score += 1000;
                }
            }

            // Assign the calculated score to the corresponding position in the pawnScores array
            pawnScores[i] = score;
        }

        return pawnScores;
    }

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
                    System.out.println("Winning shot played by Medium AI at (" + cell[0] + ", " + cell[1] + ").");
                    return;
                }
            }

            // Check for blocking move
            QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
            for (int[] cell : emptyCells) {
                int nonWinningPawnsCount = 0;
                int winningPawnsCount = 0;
                for (QuartoPawn pawn : availablePawns) {
                    if (pawn != null && !isWinningMove(grid, pawn, cell[0], cell[1])) {
                        nonWinningPawnsCount++;
                    }
                    else if (pawn!=null && isWinningMove(grid, pawn, cell[0], cell[1])){
                        winningPawnsCount++;
                    }
                }

                //if all the other pawns are winning pawns after this shot, then it is a bad shot
                boolean danger = false;
                grid[cell[0]][cell[1]] = selectedPawn;
                if (areAllPawnWinning(quartoModel))
                    danger = true;
                grid[cell[0]][cell[1]] = null;

                if (nonWinningPawnsCount % 2 == 0 && winningPawnsCount != 0 && !danger) {
                    quartoModel.playShotHuman(cell[0], cell[1]);
                    System.out.println("Blocking shot played by Medium AI at (" + cell[0] + ", " + cell[1] + ").");
                    return;
                }
            }

            // Play optimal move: prioritize 2-in-a-row, then 1-in-a-row, then 3-in-a-row
            int maxScore = Integer.MIN_VALUE;
            List<int[]> bestCells = new ArrayList<>();

            for (int[] cell : emptyCells) {
                int score = evaluatePosition(grid, selectedPawn, cell[0], cell[1]);

                // Adjust score based on desired alignments
                int alignmentScore = getAlignmentScore(grid, selectedPawn, cell[0], cell[1]);
                score += alignmentScore;

                //if all the other pawns are winning pawns after this shot, then it is a bad shot
                grid[cell[0]][cell[1]] = selectedPawn;
                if (areAllPawnWinning(quartoModel))
                    score-=10000;
                grid[cell[0]][cell[1]] = null;

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
                System.out.println("Optimal shot played by Medium AI at (" + bestCell[0] + ", " + bestCell[1] + ").");
            }
        } else {
            System.out.println("All cells are occupied. Impossible to play.");
        }
    }

    private int getAlignmentScore(QuartoPawn[][] grid, QuartoPawn pawn, int x, int y) {
        int score = 0;
        grid[x][y] = pawn;

        // Check rows, columns, and diagonals
        score += evaluateAlignment(grid, x, 0, x, 3); // Row
        score += evaluateAlignment(grid, 0, y, 3, y); // Column
        if (x == y) {
            score += evaluateAlignment(grid, 0, 0, 3, 3); // Main diagonal
        }
        if (x + y == 3) {
            score += evaluateAlignment(grid, 0, 3, 3, 0); // Anti-diagonal
        }

        grid[x][y] = null;
        return score;
    }

    private int evaluateAlignment(QuartoPawn[][] grid, int x1, int y1, int x2, int y2) {
        int dx = (x2 - x1) / 3;
        int dy = (y2 - y1) / 3;
        QuartoPawn[] line = new QuartoPawn[4];
        int alignmentScore = 0;

        for (int i = 0; i < 4; i++) {
            line[i] = grid[x1 + i * dx][y1 + i * dy];
        }

        // Check for alignments of 2, 1, and 3
        int nonNullCount = 0;
        for (QuartoPawn pawn : line) {
            if (pawn != null) {
                nonNullCount++;
            }
        }

        if (nonNullCount == 2) {
            alignmentScore += 200; // Prioritize alignments of 2
        } else if (nonNullCount == 4){
            alignmentScore += 100; // Prioritize alignments of 4
        } else if (nonNullCount == 1) {
            alignmentScore += 50;  // Prioritize alignments of 1
        } else if (nonNullCount == 3) {
            alignmentScore += 10;  // Prioritize alignments of 3
        }

        return alignmentScore;
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
