package src.model.ai;

import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;
import src.model.game.QuartoWin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The {@code EasyAIPlayer} class implements a simple AI player for the Quarto game.
 * This AI uses basic strategies to select pawns and play moves.
 * It aims to either win or block the opponent from winning if a winning move is detected.
 *
 * <p>This class provides methods to:
 * <ul>
 *   <li>Check if a list of pawns is complete</li>
 *   <li>Check if a list of pawns has potential to form a winning line</li>
 *   <li>Update characteristics based on the pawns</li>
 *   <li>Calculate characteristics from the game grid</li>
 *   <li>Calculate scores for available pawns</li>
 *   <li>Select a pawn to play</li>
 *   <li>Play a shot in the game</li>
 * </ul>
 * </p>
 *
 * <p>This implementation uses randomization for pawn selection when multiple options are equally good.
 * It also evaluates the game grid to find optimal positions for placing pawns.</p>
 *
 * @see src.model.game.QuartoModel
 * @see src.model.game.QuartoPawn
 * @see src.model.game.QuartoWin
 */

public class EasyAIPlayer implements Player {

    /**
     * Random number generator used for making random selections.
     */
    private final Random random;

    /**
     * Instance of {@code QuartoWin} used to check for winning situations.
     */
    private final QuartoWin quartoWin;

    /**
     * Constructs an {@code EasyAIPlayer} with a new random number generator
     * and a new instance of {@code QuartoWin}.
     */
    public EasyAIPlayer() {
        random = new Random();
        quartoWin = new QuartoWin();
    }

    /**
     * Checks if a list of pawns is complete. A list is considered complete if it contains exactly 4
     * {@code QuartoPawn} objects and none of them are {@code null}.
     *
     * @param pawns the array of {@code QuartoPawn} objects to check.
     * @return {@code true} if the list contains exactly 4 non-null {@code QuartoPawn} objects, {@code false} otherwise.
     * @throws IllegalArgumentException if the list does not contain exactly 4 {@code QuartoPawn} objects.
     */
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

    /**
     * Checks if a list of pawns has the potential to form a winning line.
     * A list is considered to have potential if it is not complete and
     * all non-null pawns in the list share at least one characteristic.
     *
     * @param pawns the array of {@code QuartoPawn} objects to check.
     * @return {@code true} if the list has potential, {@code false} otherwise.
     */
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

    /**
     * Updates the characteristics array based on the given list of pawns.
     * Each characteristic count is incremented based on the pawns' characteristics.
     *
     * @param characteristics the array to be updated with characteristic counts.
     * @param pawns the array of {@code QuartoPawn} objects to check.
     * @throws IllegalArgumentException if the size of the characteristics array is not 8
     *                                  or if the size of the pawns array is not 4.
     */
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


    /**
     * Calculates the characteristic counts for each row, column, and diagonal in the game grid.
     * The counts are updated only for incomplete lines that have potential to form a winning line.
     *
     * @param grid the 2D array representing the game grid.
     * @return an array of characteristic counts.
     */
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

    /**
     * Calculates the scores for each available pawn based on the current game state.
     *
     * @param quartoModel the current game model containing the state of the game.
     * @return an array of scores for each available pawn.
     */
    public int[] calculateAvailablePawnsScores(QuartoModel quartoModel) {
        QuartoPawn[] listOfPawn = quartoModel.getPawnAvailable();
        int[] pawnScores = new int[16];

        // Get the characteristics from the current grid
        int[] currentCharacteristics = calculateCharacteristicsFromGrid(quartoModel.getTable());

        // Iterate over each available pawn
        for (int i = 0; i < 16; i++) {
            int score = getScore(currentCharacteristics, listOfPawn[i]);

            // Assign the calculated score to the corresponding position in the pawnScores array
            pawnScores[i] = score;
        }

        return pawnScores;
    }

    /**
     * Calculates the score for a given pawn based on the current characteristics of the game grid.
     *
     * @param currentCharacteristics an array representing the current characteristic counts in the game grid.
     * @param pawn the {@code QuartoPawn} object to score.
     * @return the score of the pawn.
     */
    private static int getScore(int[] currentCharacteristics, QuartoPawn pawn) {
        int score = 0;
        if(pawn == null){
            score = Integer.MAX_VALUE;
        }
        else {
            // Calculate the score for the current pawn based on its characteristics
            score += pawn.isRound() ? currentCharacteristics[1] : currentCharacteristics[0];
            score += pawn.isWhite() ? currentCharacteristics[3] : currentCharacteristics[2];
            score += pawn.isLittle() ? currentCharacteristics[5] : currentCharacteristics[4];
            score += pawn.isHollow() ? currentCharacteristics[7] : currentCharacteristics[6];
        }
        return score;
    }

    /**
     * Selects a pawn for the player based on the scores calculated for available pawns.
     *
     * @param quartoModel the current game model containing the state of the game.
     */
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

    /**
     * Plays a shot for the player based on the current game state.
     *
     * @param quartoModel the current game model containing the state of the game.
     */
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
                    System.out.println("Winning shot played by Easy AI at (" + cell[0] + ", " + cell[1] + ").");
                    return;
                }
            }

            // Check for blocking move
            QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
            for (int[] cell : emptyCells) {
                boolean allWinning = true;
                for (QuartoPawn pawn : availablePawns) {
                    if (pawn != null && !isWinningMove(grid, pawn, cell[0], cell[1])) {
                        allWinning = false;
                        break;
                    }
                }
                if (allWinning) {
                    quartoModel.playShotHuman(cell[0], cell[1]);
                    System.out.println("Blocking shot played by Easy AI at (" + cell[0] + ", " + cell[1] + ").");
                    return;
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
                System.out.println("Optimal shot played by Easy AI at (" + bestCell[0] + ", " + bestCell[1] + ").");
            }
        } else {
            System.out.println("All cells are occupied. Impossible to play.");
        }
    }

    /**
     * Checks if placing a pawn at the specified position results in a winning move.
     *
     * @param grid the current game grid.
     * @param pawn the pawn to place.
     * @param x the row index to place the pawn.
     * @param y the column index to place the pawn.
     * @return {@code true} if placing the pawn results in a winning move, {@code false} otherwise.
     */
    private boolean isWinningMove(QuartoPawn[][] grid, QuartoPawn pawn, int x, int y) {
        grid[x][y] = pawn;
        boolean winning = quartoWin.winSituationLine(grid, x) ||
                quartoWin.winSituationColumn(grid, y) ||
                quartoWin.winSituationDiagonal(grid, x, y);
        grid[x][y] = null;
        return winning;
    }

    /**
     * Evaluates the optimal position to play for the selected pawn based on the current game state.
     *
     * @param grid the current game grid.
     * @param pawn the pawn to place.
     * @param x the row index to evaluate.
     * @param y the column index to evaluate.
     * @return the score of the evaluated position.
     */
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

    /**
     * Evaluates a line (row, column, or diagonal) for potential score based on the characteristics of the pawns in the line.
     *
     * @param grid the current game grid.
     * @param x1 the starting row index of the line.
     * @param y1 the starting column index of the line.
     * @param x2 the ending row index of the line.
     * @param y2 the ending column index of the line.
     * @return the score of the evaluated line.
     */
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
