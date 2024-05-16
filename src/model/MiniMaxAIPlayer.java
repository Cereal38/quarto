package src.model;

import java.util.Random;

public class MiniMaxAIPlayer implements Player {
    private int maxDepth;
    private Random random;

    public MiniMaxAIPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
        random = new Random();
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
                    selectPawn(quartoModel);
                    // Calculate the score for this move using Min-Max algorithm
                    int score = minMax(quartoModel, 0, false);

                    // Undo the simulation
                    undoSimulation(quartoModel);
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

    private int minMax(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer) {
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
                        selectPawn(quartoModel);
                        // Recursively call minMax with depth increased and switch player
                        int score = minMax(quartoModel, depth + 1, false);

                        // Undo the simulation
                        undoSimulation(quartoModel);
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
                        selectPawn(quartoModel);
                        // Recursively call minMax with depth increased and switch player
                        int score = minMax(quartoModel, depth + 1, true);

                        // Undo the simulation
                        undoSimulation(quartoModel);
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


    private int evaluateRisk (QuartoPawn[][] table, boolean isAIPlayer) {
        int riskValue = 0;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                // Evaluate lines
                if (table[i][j] != null ) {
                    riskValue++;
                }
                // Evaluate columns
                if (table[j][i] != null ) {
                    riskValue++;
                }
                // Evaluate diagonals
                if (i == j || (i + j) == 3){
                    riskValue++;
                }
            }
        }
        if (isAIPlayer){
            // Return a positive score if the current state is favorable for the AI
            return riskValue;
        }
        // Return a negative score if the current state is favorable for the opponent
        return (-1 * riskValue);
    }

    private void simulatePlacePawn (QuartoModel quartoModel, int row,  int column){
        quartoModel.playShotHuman(row, column);
    }

    private void undoSimulation (QuartoModel quartoModel){
        quartoModel.undo();
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
        int[] bestMove = getBestMove(quartoModel);
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by Minimax AI at (" + bestMove[0] +"," + bestMove[1] + ")." );
    }

}

