package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAIPlayer implements Player {
    private int maxDepth;
    private Random random;
    private QuartoPawn actualPawn;
    private int actualPawnScore;
    private Heuristics heuristics;

    public MiniMaxAIPlayer(int maxDepth, Heuristics heuristics ) {
        this.maxDepth = maxDepth;
        this.random = new Random();
        this.heuristics = heuristics;
    }

    @Override
    public void selectPawn(QuartoModel quartoModel) {
        int bestPawnIndex = getBestPawn(quartoModel);
        quartoModel.selectPawnHuman(bestPawnIndex);
        System.out.println("Pawn chosen by Minimax AI is " + bestPawnIndex + ".");
    }

    @Override
    public void playShot(QuartoModel quartoModel) {
        int[] bestMove = getBestMove(quartoModel);
        while (!quartoModel.isTableEmpty(bestMove[0], bestMove[1])){
            bestMove = getBestMove(quartoModel);
        }
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by Minimax AI at (" + bestMove[0] + "," + bestMove[1] + ").");
    }

    private int[] alphaBeta(int alpha, int beta, int score, boolean isMaximizingPlayer) {
        int[] alphaBetaValue = new int[3];
        alphaBetaValue[2] = 0;
        if (isMaximizingPlayer) {
            if (score > beta) {
                alphaBetaValue[2] = 1;
            } else {
                alpha = Math.max(alpha, score);
            }
        } else {
            if (alpha > score) {
                alphaBetaValue[2] = 1;
            } else {
                beta = Math.min(score, beta);
            }
        }
        alphaBetaValue[0] = alpha;
        alphaBetaValue[1] = beta;
        return alphaBetaValue;
    }

    private int getBestPawn(QuartoModel quartoModel) {
        int bestScore = Integer.MIN_VALUE;
        List<Integer> bestPawns = new ArrayList<>();
        QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int[] alphaBetaValue;
        //int[] actualPawnScores = calculateAvailablePawnsScores(quartoModel);

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

        return bestPawns.get(random.nextInt(bestPawns.size()));
    }

    private int[] getBestMove(QuartoModel quartoModel) {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;
        List<Integer> bestMovesAxis = new ArrayList<>();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int[] alphaBetaValue;

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
        int len = bestMovesAxis.size()/2;
        int indexAxis = random.nextInt(len)*2;
        bestMove[0] = bestMovesAxis.get(indexAxis);
        bestMove[1] = bestMovesAxis.get(indexAxis+1);
        while (quartoModel.isTableEmpty(bestMove[0], bestMove[1]) != true) {
            indexAxis = random.nextInt(len)*2;
            bestMove[0] = bestMovesAxis.get(indexAxis);
            bestMove[1] = bestMovesAxis.get(indexAxis+1);
        }
        return bestMove;
    }

    private int minimax(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer, boolean isSelectingPawn, int alpha, int beta) {
        if (depth == maxDepth || quartoModel.hasAWinner() || quartoModel.isATie()) {
            return evaluateBoard(quartoModel, isMaximizingPlayer) + evaluateRisk(quartoModel.getTable(), isMaximizingPlayer);
        }

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
        int[] alphaBetaValue;

        if (isSelectingPawn) {
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

    private int evaluateSelection(QuartoModel quartoModel, boolean isAIPlayer) {
        return evaluateCommonCharacteristics(quartoModel, isAIPlayer);
    }

    /*
    private int evaluatePlacement(QuartoModel quartoModel, boolean isAIPlayer) {
        if (quartoModel.isGameOver()) {
            return isAIPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        return evaluateRisk(quartoModel.getTable(), isAIPlayer);

    }*/

    private int evaluateCommonCharacteristics(QuartoModel quartoModel, boolean isAIPlayer) {
        int commonCharacteristics = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!quartoModel.isTableEmpty(i, j)) {
                    QuartoPawn boardPawn = quartoModel.getPawnAtPosition(i, j);
                    commonCharacteristics += countCommonCharacteristics(boardPawn);
                }
            }
        }
        return isAIPlayer ? commonCharacteristics : -commonCharacteristics;
    }

    private int evaluateRisk(QuartoPawn[][] table, boolean isAIPlayer) {
        int count = 0;
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
        return isAIPlayer ? count * heuristics.getRiskValue(): - count * heuristics.getRiskValue();
    }

    private int countCommonCharacteristics(QuartoPawn pawn2) {
        int commonCount = 0;
        if (actualPawn.isHollow() == pawn2.isHollow()) commonCount++;
        if (actualPawn.isLittle() == pawn2.isLittle()) commonCount++;
        if (actualPawn.isRound() == pawn2.isRound()) commonCount++;
        if (actualPawn.isWhite() == pawn2.isWhite()) commonCount++;
        return commonCount;
    }

    private void simulatePlacePawn(QuartoModel quartoModel, int row, int column) {
        quartoModel.playShotHuman(row, column);
    }

    private void simulateSelectPawn(QuartoModel quartoModel, int index) {
        quartoModel.selectPawnHuman(index);
    }

    private void undoSimulation(QuartoModel quartoModel) {
        quartoModel.undo();
    }

    private boolean isCompleteList(QuartoPawn[] pawns) {
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

    private int evaluateBoard(QuartoModel quartoModel, boolean isAIPlayer ) {
        int score = 0;

        // Evaluate all rows, columns and diagonals
        score += evaluateLines(quartoModel, quartoModel.getLines());
        score += evaluateLines(quartoModel, quartoModel.getColumns());
        score += evaluateLines(quartoModel, quartoModel.getDiagonals());

        return isAIPlayer ? score : -score;
    }

    private int evaluateLines(QuartoModel quartoModel, QuartoPawn[][] lines) {
        int score = 0;

        for (QuartoPawn[] line : lines) {
            score += evaluateLine(line);
        }

        return score;
    }

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
        }
        else {
            if (count == 3 && commonCharacteristics != 0)
                actualPawnScore += heuristics.getLineOfThreeValue();
            score += commonCharacteristics * heuristics.getCommonCharValue()  - (actualPawnScore * count);
        }
        return score;
    }


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