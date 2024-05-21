package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAIPlayer implements Player {
    private int maxDepth;
    private Random random;
    private QuartoPawn actualPawn;

    public MiniMaxAIPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
        this.random = new Random();
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
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by Minimax AI at (" + bestMove[0] + "," + bestMove[1] + ").");
    }

    private int getBestPawn(QuartoModel quartoModel) {
        int bestScore = Integer.MAX_VALUE;
        List<Integer> bestPawns = new ArrayList<>();
        QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();
        int[] pawnScores = calculateAvailablePawnsScores(quartoModel);

        for (int i = 0; i < 16; i++) {
            if (availablePawns[i] != null) {
                simulateSelectPawn(quartoModel, i);
                int score = minimax(quartoModel, 0, false, false);
                undoSimulation(quartoModel);
                
                if (score < bestScore) {
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

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    simulatePlacePawn(quartoModel, i, j);
                    int score = minimax(quartoModel, 0, true, true);
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
        int indexAxis = random.nextInt(len);
        bestMove[0] = bestMovesAxis.get(indexAxis);
        bestMove[1] = bestMovesAxis.get(indexAxis+1);
        return bestMove;
    }

    private int minimax(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer, boolean isSelectingPawn) {
        if (depth == maxDepth || quartoModel.isGameOver()) {
            return isSelectingPawn ? evaluateSelection(quartoModel, isMaximizingPlayer) : evaluatePlacement(quartoModel, isMaximizingPlayer);
        }

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        QuartoPawn[] availablePawns = quartoModel.getPawnAvailable();

        if (isSelectingPawn) {
            for (int i = 0; i < 16; i++) {
                if (availablePawns[i] != null) {
                    actualPawn = quartoModel.getPawn(i);
                    simulateSelectPawn(quartoModel, i);
                    int score = minimax(quartoModel, depth + 1, !isMaximizingPlayer, false);
                    undoSimulation(quartoModel);
                    bestScore = isMaximizingPlayer ? Math.max(bestScore, score) : Math.min(bestScore, score);
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (quartoModel.isTableEmpty(i, j)) {
                        simulatePlacePawn(quartoModel, i, j);
                        int score = minimax(quartoModel, depth + 1, isMaximizingPlayer, true);
                        undoSimulation(quartoModel);
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

    private int evaluatePlacement(QuartoModel quartoModel, boolean isAIPlayer) {
        if (quartoModel.isGameOver()) {
            return isAIPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        return evaluateRisk(quartoModel.getTable(), isAIPlayer);

    }

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
        int riskValue = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (table[i][j] != null) {
                    riskValue++;
                }
                if (table[j][i] != null) {
                    riskValue++;
                }
                if (i == j || (i + j) == 3) {
                    riskValue++;
                }
            }
        }
        return isAIPlayer ? riskValue : -riskValue;
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

    private void updateCharacteristics(int[] characteristics, QuartoPawn[] pawns) {
        if (characteristics.length != 8 || pawns.length != 4) {
            throw new IllegalArgumentException("Invalid input array size.");
        }

        for (QuartoPawn pawn : pawns) {
            if (pawn == null) {
                continue;
            }
            if (pawn.isRound()) {
                characteristics[1]++;
            } else {
                characteristics[0]++;
            }
            if (pawn.isWhite()) {
                characteristics[3]++;
            } else {
                characteristics[2]++;
            }
            if (pawn.isLittle()) {
                characteristics[5]++;
            } else {
                characteristics[4]++;
            }
            if (pawn.isHollow()) {
                characteristics[7]++;
            } else {
                characteristics[6]++;
            }
        }
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
            if (pawn == null) {
                score = Integer.MAX_VALUE;
            } else {
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

    
}