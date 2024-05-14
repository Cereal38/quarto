package src.model;

public class MiniMaxAIPlayer implements Player {
    private int maxDepth;

    public MiniMaxAIPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
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
                    int score = minMax(quartoModel, 0, false);

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

    private int minMax(QuartoModel quartoModel, int depth, boolean isMaximizingPlayer) {
        // Base case: if game is over ( or maximum depth is reached, return the evaluation score
        if (depth == maxDepth || quartoModel.isGameOver()) {
            return evaluate(quartoModel);
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
                        int score = minMax(quartoModel, depth + 1, false);

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
                        int score = minMax(quartoModel, depth + 1, true);

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
    private int evaluate(QuartoModel quartoModel) {
        // Implement your evaluation logic here
        // Return a positive score if the current state is favorable for the AI
        // Return a negative score if the current state is favorable for the opponent
        // Return 0 if the game is balanced
        // The score should be based on factors such as potential winning positions, pawn combinations, etc.
        // This is a placeholder, you may need to customize it based on your game's dynamics
        return 0;
    }

    private void simulatePlacePawn (QuartoModel quartoModel, int row,  int colomn){
        quartoModel.playShot(row, colomn);
    }

    private void undoSimulation (QuartoModel quartoModel){
        quartoModel.undo();
    }

    @Override
    public void selectPawn(QuartoModel quartoModel){
        quartoModel.selectPawnHuman(0);
    }

    @Override
    public void playShot(QuartoModel quartoModel) {
        int[] bestMove = new int[2];
        bestMove = getBestMove(quartoModel);
        quartoModel.playShotHuman(bestMove[0], bestMove[1]);
        System.out.println("Shot played by AI at (" + bestMove[0] +"," + bestMove[1] + ")." );
    }

}

