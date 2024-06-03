package src.model;

public class QuartoPlayerManager {
    private int currentPlayer; // 1 for Player 1 and 2 for Player 2
    private int[] playerType = new int[2];
    private Player randomAIPlayer, easyAIPlayer, mediumAIPlayer, minimaxAIPlayer1, minimaxAIPlayer2;
    private Heuristics heuristic1;
    private Heuristics heuristic2;
    private String firstPlayerName, secondPlayerName;

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

    private void initializeAIPlayers() {
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public int getCurrentPlayerType() {
        return playerType[getCurrentPlayer() - 1];
    }

    public void selectPawn(QuartoModel model, int indexPawn) {
        if (getCurrentPlayerType() == 0) {
            if (model.getBoard().getPawnAvailable()[indexPawn] != null) {
                model.selectPawnHuman(indexPawn);
            }
            return;
        }

        // Add 1 second delay when AI is selecting a pawn
        model.delay(1000);
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

    public void playShot(QuartoModel model, int line, int column) {
        if (getCurrentPlayerType() == 0) {
            model.playShotHuman(line, column);
            return;
        }
        // Add 1 second delay when AI is playing
        model.delay(1000);
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

    public String getNameOfTheCurrentPlayer() {
        return (currentPlayer == 1) ? firstPlayerName : secondPlayerName;
    }

    public String getPlayer1Name() {
        return firstPlayerName;
    }

    public String getPlayer2Name() {
        return secondPlayerName;
    }

    public int getPlayer1Type() {
        return playerType[0];
    }

    public int getPlayer2Type() {
        return playerType[1];
    }

    public void setPlayer1Name(String playerName) {
        this.firstPlayerName = playerName;
    }

    public void setPlayer1Type(int playerType) {
        this.playerType[0] = playerType;
    }

    public int[] getPlayerType() {
        return playerType;
    }
}
