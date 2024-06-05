package src.model.ai;

import src.model.game.QuartoHistory;
import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The MiniMaxAIPlayer class represents an AI player that uses the MiniMax algorithm with alpha-beta pruning
 * to select the best pawn and play the best move in a game of Quarto.
 * It evaluates the game board using heuristics and adjusts the maximum depth of the search based on the number
 * of free spaces on the board, aiming to select the most favorable moves.
 */

public class MiniMaxAIPlayer implements Player {
    /**
     * Represents the maximum depth for the MiniMax algorithm.
     */
    private int maxDepth;

    /**
     * Random number generator used for choosing among equally good moves.
     */
    private Random random;

    /**
     * The pawn currently selected by the AI.
     */
    private QuartoPawn actualPawn;

    /**
     * The score of the currently selected pawn.
     */
    private int actualPawnScore;

    /**
     * Heuristic values used for evaluating the board.
     */
    private Heuristics heuristics;

    /**
     * The count of moves made by the AI.
     */
    private int countMoves = 0;

    /**
     * Initializes the MiniMaxAIPlayer with a specified maximum depth and heuristic values.
     *
     * @param maxDepth   The maximum depth for the MiniMax algorithm.
     * @param heuristics Heuristic values for evaluating the board.
     */
    public MiniMaxAIPlayer(int maxDepth, Heuristics heuristics) {
        this.maxDepth = maxDepth;
        this.random = new Random();
        this.heuristics = heuristics;
    }

    /**
     * Counts the number of free spaces on the game board.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     * @return The number of free spaces on the game board.
     */
    private int getFreeSpaces(QuartoModel quartoModel) {
        int freeSpaces = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    freeSpaces++;
                }
            }
        }
        return freeSpaces;
    }


    /**
     * Adjusts the maximum depth of the MiniMax algorithm based on the number of free spaces on the game board.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    private void adjustMaxDepth(QuartoModel quartoModel) {
        int freeSpaces = getFreeSpaces(quartoModel);
        if (freeSpaces >= 11) {  // Early game
            maxDepth = 2;
        } else if (freeSpaces >= 9) {  // Mid game
            maxDepth = 3;
        } else if (freeSpaces >= 7) {  // Late game
            maxDepth = 4;
        }else if (freeSpaces >= 5) {  // Late game
            maxDepth = 5;
        }  else {  // End game
            maxDepth = 6;
        }
    }

    /**
     * Selects the best pawn using the MiniMax algorithm and makes the selection in the QuartoModel.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    @Override
    public void selectPawn(QuartoModel quartoModel) {
        adjustMaxDepth(quartoModel);
        int bestPawnIndex = getBestPawn(quartoModel);
        quartoModel.selectPawnHuman(bestPawnIndex);
        System.out.println("Pawn chosen by Minimax AI is " + bestPawnIndex + ".");
    }

    /**
     * Plays the best move using the MiniMax algorithm and performs the move in the QuartoModel.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    @Override
    public void playShot(QuartoModel quartoModel) {
        adjustMaxDepth(quartoModel);
        int[] bestMove = getBestMove(quartoModel);
        while (!quartoModel.isTableEmpty(bestMove[0], bestMove[1])) {
            bestMove = getBestMove(quartoModel);
        }
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by Minimax AI at (" + bestMove[0] + "," + bestMove[1] + ").");
    }

    /**
     * Helper method implementing alpha-beta pruning for the MiniMax algorithm.
     *
     * @param alpha               The current alpha value.
     * @param beta                The current beta value.
     * @param score               The score to evaluate.
     * @param isMaximizingPlayer  Indicates whether the current player is maximizing.
     * @return                    An array containing the updated alpha, beta, and a flag indicating if pruning occurred.
     */
    private int[] alphaBeta(int alpha, int beta, int score, boolean isMaximizingPlayer) {
        int[] alphaBetaValue = new int[3];
        alphaBetaValue[2] = 0; // To indicate if pruning has occurred
        if (isMaximizingPlayer) {
            if (score > beta) {
                alphaBetaValue[2] = 1; // Prune the branch
            } else {
                alpha = Math.max(alpha, score);
            }
        } else {
            if (alpha > score) {
                alphaBetaValue[2] = 1; // Prune the branch
            } else {
                beta = Math.min(score, beta);
            }
        }
        alphaBetaValue[0] = alpha;
        alphaBetaValue[1] = beta;
        return alphaBetaValue;
    }

    /**
     * Finds the best pawn to select using the MiniMax algorithm.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     * @return            The index of the best pawn to select.
     */
    public int getBestPawn(QuartoModel quartoModel) {
        QuartoHistory next = quartoModel.getNext();
        int bestScore = Integer.MIN_VALUE;
        List<Integer> bestPawns = new ArrayList<>();
        QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int[] alphaBetaValue;

        // Iterate through all pawns to find the best one
        for (int i = 0; i < 16; i++) {
            if (availablePawns[i] != null) {
                simulateSelectPawn(quartoModel, i);
                int score = minimax(quartoModel, 0, false, false, alpha, beta);
                undoSimulation(quartoModel);
                alphaBetaValue = alphaBeta(alpha, beta, score, true);
                alpha = alphaBetaValue[0];
                beta = alphaBetaValue[1];
                if (score > bestScore) {
                    bestScore = score;
                    bestPawns.clear();
                    bestPawns.add(i);
                } else if (score == bestScore) {
                    bestPawns.add(i);
                }
            }
        }
        countMoves += 2;

        quartoModel.setNext(next);
        // Randomly choose among the best pawns
        return bestPawns.get(random.nextInt(bestPawns.size()));
    }

    /**
     * Finds the best move to play using the MiniMax algorithm.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     * @return            An array containing the coordinates of the best move [row, column].
     */
    public int[] getBestMove(QuartoModel quartoModel) {
        QuartoHistory next = quartoModel.getNext();
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;
        List<Integer> bestMovesAxis = new ArrayList<>();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int[] alphaBetaValue;

        // Iterate through all possible moves to find the best one
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    simulatePlacePawn(quartoModel, i, j);
                    int score = minimax(quartoModel, 0, true, true, alpha, beta);
                    alphaBetaValue = alphaBeta(alpha, beta, score, true);
                    alpha = alphaBetaValue[0];
                    beta = alphaBetaValue[1];
                    undoSimulation(quartoModel);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMovesAxis.clear();
                        bestMovesAxis.add(i);
                        bestMovesAxis.add(j);
                    } else if (score == bestScore) {
                        bestMovesAxis.add(i);
                        bestMovesAxis.add(j);
                    }
                }
            }
        }
        // Randomly choose among the best moves
        int len = bestMovesAxis.size() / 2;
        int indexAxis = random.nextInt(len) * 2;
        bestMove[0] = bestMovesAxis.get(indexAxis);
        bestMove[1] = bestMovesAxis.get(indexAxis + 1);
        while (!quartoModel.isTableEmpty(bestMove[0], bestMove[1])) {
            indexAxis = random.nextInt(len) * 2;
            bestMove[0] = bestMovesAxis.get(indexAxis);
            bestMove[1] = bestMovesAxis.get(indexAxis + 1);
        }
        quartoModel.setNext(next);
        return bestMove;
    }

    /**
     * Performs the MiniMax algorithm with alpha-beta pruning to find the best move or pawn selection.
     *
     * @param quartoModel         The QuartoModel representing the current state of the game.
     * @param depth               The current depth in the MiniMax search tree.
     * @param isMaximizingPlayer  A boolean indicating whether the current player is maximizing or minimizing.
     * @param isSelectingPawn     A boolean indicating whether the current action is selecting a pawn.
     * @param alpha               The alpha value for alpha-beta pruning.
     * @param beta                The beta value for alpha-beta pruning.
     * @return                    The best score for the current state of the game.
     */
    private int minimax(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer, boolean isSelectingPawn, int alpha, int beta) {
        // Base case: if maximum depth reached or game is over
        if (depth == maxDepth || quartoModel.hasAWinner() || quartoModel.isATie()) {
            return evaluateBoard(quartoModel, isMaximizingPlayer) + evaluateRisk(quartoModel.getTable(), isMaximizingPlayer);
        }

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
        int[] alphaBetaValue;

        if (isSelectingPawn) {
            // Simulate selecting a pawn
            for (int i = 0; i < 16; i++) {
                if (availablePawns[i] != null) {
                    actualPawn = quartoModel.getPawn(i);
                    simulateSelectPawn(quartoModel, i);
                    int score = minimax(quartoModel, depth, !isMaximizingPlayer, false, alpha, beta);
                    undoSimulation(quartoModel);

                    alphaBetaValue = alphaBeta(alpha, beta, score, isMaximizingPlayer);
                    alpha = alphaBetaValue[0];
                    beta = alphaBetaValue[1];
                    if (alphaBetaValue[2] == 1)
                        return score;
                    bestScore = isMaximizingPlayer ? Math.max(bestScore, score) : Math.min(bestScore, score);
                }
            }
        } else {
            // Simulate placing a pawn
            QuartoPawn copyActualPawn = actualPawn;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (quartoModel.isTableEmpty(i, j)) {
                        simulatePlacePawn(quartoModel, i, j);
                        actualPawn = null;
                        int score = minimax(quartoModel, depth + 1, isMaximizingPlayer, true, alpha, beta);
                        undoSimulation(quartoModel);
                        alphaBetaValue = alphaBeta(alpha, beta, score, isMaximizingPlayer);
                        alpha = alphaBetaValue[0];
                        beta = alphaBetaValue[1];
                        actualPawn = copyActualPawn;
                        if (alphaBetaValue[2] == 1)
                            return score;
                        bestScore = isMaximizingPlayer ? Math.max(bestScore, score) : Math.min(bestScore, score);
                    }
                }
            }
        }
        return bestScore;
    }

    /**
     * Evaluates the risk on the board based on the number of pawns on the board.
     *
     * @param table      The 2D array representing the game board.
     * @param isAIPlayer A boolean indicating whether the AI player is evaluating the risk.
     * @return The risk value based on the number of pawns on the board.
     */
    private int evaluateRisk(QuartoPawn[][] table, boolean isAIPlayer) {
        int count = 0;
        // Count the number of occupied spaces on the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (table[i][j] != null) {
                    count++;
                }
                if (table[j][i] != null) {
                    count++;
                }
                if (i == j || (i + j) == 3) {
                    count++;
                }
            }
        }
        return isAIPlayer ? count * heuristics.getRiskValue() : -count * heuristics.getRiskValue();
    }

    /**
     * Simulates placing a pawn on the board.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     * @param row         The row index where the pawn will be placed.
     * @param column      The column index where the pawn will be placed.
     */
    private void simulatePlacePawn(QuartoModel quartoModel, int row, int column) {
        quartoModel.playShotHuman(row, column);
    }

    /**
     * Simulates selecting a pawn.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     * @param index       The index of the pawn to be selected.
     */
    private void simulateSelectPawn(QuartoModel quartoModel, int index) {
        quartoModel.selectPawnHuman(index);
    }

    /**
     * Undoes the last simulation.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    private void undoSimulation(QuartoModel quartoModel) {
        quartoModel.undo();
    }

    /**
     * Evaluates the board for the current state.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     * @param isAIPlayer  A boolean indicating whether the AI player is evaluating the board.
     * @return The evaluation score of the board.
     */
    private int evaluateBoard(QuartoModel quartoModel, boolean isAIPlayer) {
        int score = 0;
        // Evaluate all rows, columns, and diagonals
        score += evaluateLines(quartoModel.getLines());
        score += evaluateLines(quartoModel.getColumns());
        score += evaluateLines(quartoModel.getDiagonals());

        return isAIPlayer ? score : -score;
    }


    // Evaluate all lines (rows, columns, or diagonals)
    /**
     * Evaluates an array of lines (rows, columns, or diagonals).
     *
     * @param lines The array of lines to be evaluated.
     * @return The total score obtained from evaluating all lines.
     */
    private int evaluateLines(QuartoPawn[][] lines) {
        int score = 0;
        for (QuartoPawn[] line : lines) {
            score += evaluateLine(line);
        }
        return score;
    }

    /**
     * Evaluates a single line (row, column, or diagonal).
     *
     * @param line The line to be evaluated.
     * @return The score obtained from evaluating the line.
     */
    private int evaluateLine(QuartoPawn[] line) {
        int score = 0;
        int commonCharacteristics = countCommonCharacteristicsInLine(line);
        int count = 0;
        for (QuartoPawn pawn : line) {
            if (pawn != null) {
                count++;
            }
        }
        if (commonCharacteristics > 0 && count == 4) {
            score += heuristics.getWinStateValue(); // Winning state
        } else {
            if (count == 3 && commonCharacteristics != 0)
                actualPawnScore += heuristics.getLineOfThreeValue();
            score += commonCharacteristics * heuristics.getCommonCharValue() - (actualPawnScore * count);
        }
        return score;
    }

    /**
     * Counts the common characteristics in a line.
     *
     * @param line The line to be evaluated.
     * @return The number of common characteristics in the line.
     */
    private int countCommonCharacteristicsInLine(QuartoPawn[] line) {
        actualPawnScore = 0;
        if (line.length != 4) {
            throw new IllegalArgumentException("The line must contain exactly 4 QuartoPawn objects.");
        }

        int commonCount = 0;
        QuartoPawn firstPawn = null;

        // Find the first non-null pawn to use as the reference
        for (QuartoPawn pawn : line) {
            if (pawn != null) {
                firstPawn = pawn;
                break;
            }
        }

        if (firstPawn == null) {
            return 0; // If all pawns are null, no common characteristics
        }

        boolean sameRound = true;
        boolean sameWhite = true;
        boolean sameLittle = true;
        boolean sameHollow = true;

        // Compare the characteristics of all pawns with the reference pawn
        for (QuartoPawn pawn : line) {
            if (pawn != null && pawn != firstPawn) {
                sameRound &= (firstPawn.isRound() == pawn.isRound());
                sameWhite &= (firstPawn.isWhite() == pawn.isWhite());
                sameLittle &= (firstPawn.isLittle() == pawn.isLittle());
                sameHollow &= (firstPawn.isHollow() == pawn.isHollow());
            }
        }

        if (sameRound) {
            if (actualPawn != null && firstPawn.isRound() == actualPawn.isRound())
                actualPawnScore += heuristics.getSameCharValue();
            commonCount++;
        }
        if (sameWhite) {
            if (actualPawn != null && firstPawn.isWhite() == actualPawn.isWhite())
                actualPawnScore += heuristics.getSameCharValue();
            commonCount++;
        }
        if (sameLittle) {
            if (actualPawn != null && firstPawn.isLittle() == actualPawn.isLittle())
                actualPawnScore += heuristics.getSameCharValue();
            commonCount++;
        }
        if (sameHollow) {
            if (actualPawn != null && firstPawn.isHollow() == actualPawn.isHollow())
                actualPawnScore += heuristics.getSameCharValue();
            commonCount++;
        }

        return commonCount;
    }
}