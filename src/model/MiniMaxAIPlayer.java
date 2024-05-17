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
        random = new Random();
    }

    public int getBestPawn(QuartoModel quartoModel) {
        int bestScore = Integer.MAX_VALUE;
        List<Integer> leastAdvantageousPawnsIndices = new ArrayList<>();
        QuartoPawn[] listOfPawn = quartoModel.getPawnAvailable();
        
        // Iterate over all possible pawns
        for (int i = 0; i < 16; i++) {
            if (listOfPawn[i] != null) {

                // Update the actual pawn
                actualPawn = quartoModel.getPawn(i);
                // Simulate placing a pawn at this position
                simulateSelectPawn(quartoModel, i);
                // Calculate the score for this move using Min-Max algorithm
                int score = minMaxMove(quartoModel, 0, false);

                // Undo the simulation
                undoSimulation(quartoModel);

                // If the calculated score is better than the best found so far, update bestMove and bestScore
                if (score < bestScore) {
                    bestScore = score;
                    leastAdvantageousPawnsIndices.clear();
                    leastAdvantageousPawnsIndices.add(i);
                } else if (score == bestScore) {
                    leastAdvantageousPawnsIndices.add(i);
                }
            }
        }
        int len = leastAdvantageousPawnsIndices.size();
        int indexPawn = random.nextInt(len);
        return leastAdvantageousPawnsIndices.get(indexPawn);
    }

    public int[] getBestMove(QuartoModel quartoModel) {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;

        // Iterate over all possible moves
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (quartoModel.isTableEmpty(i, j)) {
                    // Simulate placing a pawn at this position
                    simulatePlacePawn(quartoModel, i, j);
                    // Calculate the score for this move using Min-Max algorithm
                    int score = minMaxPawn(quartoModel, 0, true);

                    // Undo the simulation
                    undoSimulation(quartoModel);

                    // If the calculated score is better than the best found so far, update bestMove and bestScore
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minMaxPawn(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer) {
        // Base case: if game is over ( or maximum depth is reached, return the evaluation score
        if (depth == maxDepth || quartoModel.isGameOver()) {
            return evaluateCommonCharacteristics(quartoModel, isMaximizingPlayer);
        }
        
        QuartoPawn[] listOfPawn = quartoModel.getPawnAvailable();
        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            // Iterate over all possible moves
            for (int i = 0; i < 16; i++) {
                if (listOfPawn[i] != null) {
                    // Update the actual pawn
                    actualPawn = quartoModel.getPawn(i);
                    // Simulate placing a pawn at this position
                    simulateSelectPawn(quartoModel, i);
                    // Calculate the score for this move using Min-Max algorithm
                    int score = minMaxMove(quartoModel, depth+ 1, false);

                    // Undo the simulation
                    undoSimulation(quartoModel);
                    
                    // Update the bestScore
                    bestScore = Math.max(bestScore, score);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            // Iterate over all possible moves
            for (int i = 0; i < 16; i++) {          
                if (listOfPawn[i] != null) {
                    // Simulate placing a pawn at this position
                    simulateSelectPawn(quartoModel, i);
                    // Calculate the score for this move using Min-Max algorithm
                    int score = minMaxMove(quartoModel, depth + 1, true);

                    // Undo the simulation
                    undoSimulation(quartoModel);
                    
                    // Update the bestScore
                    bestScore = Math.min(bestScore, score);
                }
            }
            return bestScore;
        }
    }
    

    private int minMaxMove(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer) {
        // Base case: if game is over ( or maximum depth is reached, return the evaluation score
        if (depth == maxDepth || quartoModel.isGameOver()) {
            return evaluate(quartoModel,isMaximizingPlayer);
        }

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            // Iterate over all possible moves
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (quartoModel.isTableEmpty(i, j)) {
                        // Simulate placing a pawn at this position
                        simulatePlacePawn(quartoModel, i, j);
                        // Recursively call minMax with depth increased and switch player
                        int score = minMaxPawn(quartoModel, depth + 1, true);

                        // Undo the simulation
                        undoSimulation(quartoModel);

                        // Update the bestScore
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            // Iterate over all possible moves
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (quartoModel.isTableEmpty(i, j)) {
                        // Simulate placing a pawn at this position
                        simulatePlacePawn(quartoModel, i, j);
                        // Recursively call minMax with depth increased and switch player
                        int score = minMaxPawn(quartoModel, depth + 1, false);

                        // Undo the simulation
                        undoSimulation(quartoModel);

                        // Update the bestScore
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
    }

    // Evaluation function (heuristic) to evaluate the game state
    private int evaluate(QuartoModel quartoModel, boolean isAIPlayer) {
        // Implement your evaluation logic here
        // Return a positive score if the current state is favorable for the AI
        // Return a negative score if the current state is favorable for the opponent
        // Return 0 if the game is balanced
        // The score should be based on factors such as potential winning positions, pawn combinations, etc.
        return evaluateRisk(quartoModel.getTable(),isAIPlayer);
    }


    private int evaluateRisk(QuartoPawn[][] table, boolean isAIPlayer) {
        int riskValue = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Evaluate lines
                if (table[i][j] != null) {
                    riskValue++;
                }
                // Evaluate columns
                if (table[j][i] != null) {
                    riskValue++;
                }
                // Evaluate diagonals
                if (i == j || (i + j) == 3) {
                    riskValue++;
                }
            }
        }
        if (isAIPlayer) {
            // Return a positive score if the current state is favorable for the AI
            return riskValue;
        }
        // Return a negative score if the current state is favorable for the opponent
        return (-1 * riskValue);
    }

    private int evaluateCommonCharacteristics(QuartoModel quartoModel , boolean isAIPlayer) {
        int commonCharacteristics = 0;
        // Iterate through the board to find lines that are nearly completed
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!quartoModel.isTableEmpty(i, j)) {
                    QuartoPawn boardPawn = quartoModel.getPawnAtPosition(i, j);
                    commonCharacteristics += countCommonCharacteristics(boardPawn);
                }
            }
        }
        if (isAIPlayer) {
            return commonCharacteristics;
        }
        return (-1 * commonCharacteristics);
    }
    
    private int countCommonCharacteristics(QuartoPawn pawn2) {
        int commonCount = 0;
        if (actualPawn.isHollow() == pawn2.isHollow())
            commonCount++;
        if (actualPawn.isLittle() == pawn2.isLittle())
            commonCount++;
        if (actualPawn.isRound() == pawn2.isRound())
            commonCount++;
        if (actualPawn.isWhite() == pawn2.isWhite())
            commonCount++;
        return commonCount;
    }

    private void simulatePlacePawn(QuartoModel quartoModel, int row, int column) {
        quartoModel.playShotHuman(row, column);
    }
    
    private void simulateSelectPawn (QuartoModel quartoModel, int index){
        quartoModel.selectPawnHuman(index);
    }

    private void undoSimulation(QuartoModel quartoModel) {
        quartoModel.undo();
    }

    @Override
    public void selectPawn(QuartoModel quartoModel) {
        int bestPawnIndex = getBestPawn(quartoModel);
        quartoModel.selectPawnHuman(bestPawnIndex);
        System.out.println("Pawn chosen by Minimax AI is "+ bestPawnIndex +"." );
    }

    /*@Override
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
    }*/

    @Override
    public void playShot(QuartoModel quartoModel) {
        int[] bestMove = getBestMove(quartoModel);
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by Minimax AI at (" + bestMove[0] +"," + bestMove[1] + ")." );
    }

}

