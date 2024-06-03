package src.model.ai;

import src.model.game.QuartoHistory;
import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAIPlayer implements Player {
    private int maxDepth; // Maximum depth for the minimax algorithm
    private Random random; // Random number generator for choosing among equally good moves
    private QuartoPawn actualPawn; // The currently selected pawn
    private int actualPawnScore; // Score of the currently selected pawn
    private Heuristics heuristics; // Heuristic values for evaluating the board

    // Constructor to initialize the AI player with a specified max depth and heuristics
    public MiniMaxAIPlayer(int maxDepth, Heuristics heuristics) {
        this.maxDepth = maxDepth;
        this.random = new Random();
        this.heuristics = heuristics;
    }

    // Method to select the best pawn using minimax algorithm
    @Override
    public void selectPawn(QuartoModel quartoModel) {
        int bestPawnIndex = getBestPawn(quartoModel);
        quartoModel.selectPawnHuman(bestPawnIndex);
        System.out.println("Pawn chosen by Minimax AI is " + bestPawnIndex + ".");
    }

    // Method to play the best move using minimax algorithm
    @Override
    public void playShot(QuartoModel quartoModel) {
        int[] bestMove = getBestMove(quartoModel);
        while (!quartoModel.isTableEmpty(bestMove[0], bestMove[1])) {
            bestMove = getBestMove(quartoModel);
        }
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by Minimax AI at (" + bestMove[0] + "," + bestMove[1] + ").");
    }

    // Alpha-beta pruning helper method
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

    // Method to find the best pawn to select using minimax algorithm
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

        quartoModel.setNext(next);
        // Randomly choose among the best pawns
        return bestPawns.get(random.nextInt(bestPawns.size()));
    }

    // Method to find the best move to play using minimax algorithm
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

    // Minimax algorithm with alpha-beta pruning
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

    // Evaluate the risk on the board based on the number of pawns on the board
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

    // Simulate placing a pawn on the board
    private void simulatePlacePawn(QuartoModel quartoModel, int row, int column) {
        quartoModel.playShotHuman(row, column);
    }

    // Simulate selecting a pawn
    private void simulateSelectPawn(QuartoModel quartoModel, int index) {
        quartoModel.selectPawnHuman(index);
    }

    // Undo the last simulation
    private void undoSimulation(QuartoModel quartoModel) {
        quartoModel.undo();
    }

    // Evaluate the board for the current state
    private int evaluateBoard(QuartoModel quartoModel, boolean isAIPlayer) {
        int score = 0;
        // Evaluate all rows, columns, and diagonals
        score += evaluateLines(quartoModel.getLines());
        score += evaluateLines(quartoModel.getColumns());
        score += evaluateLines(quartoModel.getDiagonals());

        return isAIPlayer ? score : -score;
    }

    // Evaluate all lines (rows, columns, or diagonals)
    private int evaluateLines(QuartoPawn[][] lines) {
        int score = 0;
        for (QuartoPawn[] line : lines) {
            score += evaluateLine(line);
        }
        return score;
    }

    // Evaluate a single line (row, column, or diagonal)
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

    // Count the common characteristics in a line
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
