package src.model.game;

import src.model.ai.*;

/**
 * The QuartoPlayerManager class manages the players in a game of Quarto.
 * It handles player types, AI players, current player state, and player actions.
 */

public class QuartoPlayerManager {
    /** The index of the current player (1 for Player 1 and 2 for Player 2). */
    private int currentPlayer;

    /** An array representing the types of players (0 for human, 1-4 for different AI levels). */
    private int[] playerType = new int[2];

    /** The names of the first and second players. */
    private String firstPlayerName, secondPlayerName;

    /** AI players for different difficulty levels. */
    private Player randomAIPlayer, easyAIPlayer, mediumAIPlayer, minimaxAIPlayer1, minimaxAIPlayer2;

    /** Heuristics used by the AI players. */
    private Heuristics heuristic1, heuristic2;

    /**
     * Constructs a new QuartoPlayerManager instance with specified player types, names, and heuristics.
     *
     * @param firstPlayerType  The type of the first player.
     * @param secondPlayerType The type of the second player.
     * @param firstPlayerName  The name of the first player.
     * @param secondPlayerName The name of the second player.
     * @param heuristic1       The heuristics used by the first AI player.
     * @param heuristic2       The heuristics used by the second AI player.
     */
    public QuartoPlayerManager(int firstPlayerType, int secondPlayerType, String firstPlayerName, String secondPlayerName, Heuristics heuristic1, Heuristics heuristic2) {
        this.heuristic1 = heuristic1;
        this.heuristic2 = heuristic2;
        currentPlayer = 1;
        playerType[0] = firstPlayerType;
        playerType[1] = secondPlayerType;
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        initializeAIPlayers();
    }

    /**
     * Constructs a dummy QuartoPlayerManager instance for loading purposes.
     */
    public QuartoPlayerManager() {
        this.heuristic1 = new Heuristics();
        this.heuristic2 = new Heuristics();
        currentPlayer = 1;
        playerType[0] = 0;
        playerType[1] = 0;
        this.firstPlayerName = "a";
        this.secondPlayerName = "b";
    }

    /**
     * Initializes the AI players based on player types.
     */
    public void initializeAIPlayers() {
        if (playerType[0] == 1 || playerType[1] == 1) {
            randomAIPlayer = new RandomAIPlayer();
        }
        if (playerType[0] == 2 || playerType[1] == 2) {
            easyAIPlayer = new EasyAIPlayer();
        }
        if (playerType[0] == 3 || playerType[1] == 3) {
            mediumAIPlayer = new MediumAIPlayer();
        }
        if (playerType[0] == 4) {
            minimaxAIPlayer1 = new MiniMaxAIPlayer(2, heuristic1);
        }
        if (playerType[1] == 4) {
            minimaxAIPlayer2 = new MiniMaxAIPlayer(2, heuristic2);
        }
    }

    /**
     * Gets the index of the current player.
     *
     * @return The index of the current player.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Switches the current player to the other player.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    /**
     * Gets the type of the current player.
     *
     * @return The type of the current player.
     */
    public int getCurrentPlayerType() {
        return playerType[getCurrentPlayer()-1];
    }

    /**
     * Selects a pawn based on the current player type.
     *
     * @param model     The QuartoModel instance.
     * @param indexPawn The index of the pawn to select.
     */
    public void selectPawn(QuartoModel model, int indexPawn) {
        // Human player
        if (getCurrentPlayerType() == 0) {
            if (model.getBoard().getPawnAvailable()[indexPawn] != null) {
                model.selectPawnHuman(indexPawn);
            }
            return;
        }

        // AI player
        model.delay(500); // Add 1 second delay when AI is selecting a pawn
        if (getCurrentPlayerType() == 1) {
            randomAIPlayer.selectPawn(model);
        } else if (getCurrentPlayerType() == 2) {
            easyAIPlayer.selectPawn(model);
        } else if (getCurrentPlayerType() == 3) {
            mediumAIPlayer.selectPawn(model);
        } else if (getCurrentPlayerType() == 4) {
            if (currentPlayer == 1) {
                minimaxAIPlayer1.selectPawn(model);
            } else {
                minimaxAIPlayer2.selectPawn(model);
            }
        }
    }

    /**
     * Plays a shot based on the current player type.
     *
     * @param model  The QuartoModel instance.
     * @param line   The line index of the shot.
     * @param column The column index of the shot.
     */
    public void playShot(QuartoModel model, int line, int column) {
        if (getCurrentPlayerType() == 0) {
            model.playShotHuman(line, column);
            return;
        }
        // Add 1 second delay when AI is playing
        model.delay(500);
        if (getCurrentPlayerType() == 1) {
            randomAIPlayer.playShot(model);
        } else if (getCurrentPlayerType() == 2) {
            easyAIPlayer.playShot(model);
        } else if (getCurrentPlayerType() == 3) {
            mediumAIPlayer.playShot(model);
        } else if (getCurrentPlayerType() == 4) {
            if (currentPlayer == 1) {
                minimaxAIPlayer1.playShot(model);
            } else {
                minimaxAIPlayer2.playShot(model);
            }
        } else {
            model.playShotHuman(line, column);
        }
    }

    /**
     * Gets the name of the current player.
     *
     * @return The name of the current player.
     */
    public String getNameOfTheCurrentPlayer() {
        return (currentPlayer == 1) ? firstPlayerName : secondPlayerName;
    }

    /**
     * Gets the name of Player 1.
     *
     * @return The name of Player 1.
     */
    public String getPlayer1Name() {
        return firstPlayerName;
    }

    /**
     * Gets the name of Player 2.
     *
     * @return The name of Player 2.
     */
    public String getPlayer2Name() {
        return secondPlayerName;
    }

    /**
     * Gets the type of Player 1.
     *
     * @return The type of Player 1.
     */
    public int getPlayer1Type() {
        return playerType[0];
    }

    /**
     * Gets the type of Player 2.
     *
     * @return The type of Player 2.
     */
    public int getPlayer2Type() {
        return playerType[1];
    }

    /**
     * Sets the name of Player 1.
     *
     * @param playerName The name of Player 1.
     */
    public void setPlayer1Name(String playerName) {
        this.firstPlayerName = playerName;
    }

    /**
     * Sets the type of Player 1.
     *
     * @param playerType The type of Player 1.
     */
    public void setPlayer1Type(int playerType) {
        this.playerType[0] = playerType;
    }

    /**
     * Gets an array containing the types of players.
     *
     * @return An array containing the types of players.
     */
    public int[] getPlayerType() {
        return playerType;
    }

    /**
     * Sets the name of Player 2.
     *
     * @param playerName The name of Player 2.
     */
    public void setPlayer2Name(String playerName) {
        this.secondPlayerName = playerName;
    }

    /**
     * Sets the type of Player 2.
     *
     * @param playerType The type of Player 2.
     */
    public void setPlayer2Type(int playerType) {
        this.playerType[1] = playerType;
    }
}

