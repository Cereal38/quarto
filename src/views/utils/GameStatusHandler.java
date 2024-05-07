package src.views.utils;

public class GameStatusHandler {

  // Constants
  public static final int GAME_NOT_STARTED = 0;
  public static final int PLAYER_ONE_PLAY_PAWN = 1;
  public static final int PLAYER_ONE_SELECT_PAWN = 2;
  public static final int PLAYER_TWO_PLAY_PAWN = 3;
  public static final int PLAYER_TWO_SELECT_PAWN = 4;
  public static final int PLAYER_ONE_WIN = 5;
  public static final int PLAYER_TWO_WIN = 6;

  private static int gamePhase = GAME_NOT_STARTED;

  public static void setGamePhase(int phase) {
    gamePhase = phase;
  }

  public static void startGame() {
    gamePhase = PLAYER_ONE_SELECT_PAWN;
  }

  public static int getGamePhase() {
    return gamePhase;
  }

  public static boolean isPlayerOneTurn() {
    return gamePhase == PLAYER_ONE_PLAY_PAWN || gamePhase == PLAYER_ONE_SELECT_PAWN;
  }

  public static boolean isPlayerTwoTurn() {
    return gamePhase == PLAYER_TWO_PLAY_PAWN || gamePhase == PLAYER_TWO_SELECT_PAWN;
  }

  public static boolean isSelectionPhase() {
    return gamePhase == PLAYER_ONE_SELECT_PAWN || gamePhase == PLAYER_TWO_SELECT_PAWN;
  }

  public static boolean isPlayPhase() {
    return gamePhase == PLAYER_ONE_PLAY_PAWN || gamePhase == PLAYER_TWO_PLAY_PAWN;
  }

  public static void nextPhase() {
    switch (gamePhase) {
    case PLAYER_ONE_PLAY_PAWN:
      gamePhase = PLAYER_ONE_SELECT_PAWN;
      break;
    case PLAYER_ONE_SELECT_PAWN:
      gamePhase = PLAYER_TWO_PLAY_PAWN;
      break;
    case PLAYER_TWO_PLAY_PAWN:
      gamePhase = PLAYER_TWO_SELECT_PAWN;
      break;
    case PLAYER_TWO_SELECT_PAWN:
      gamePhase = PLAYER_ONE_PLAY_PAWN;
      break;
    default:
      break;
    }
  }

  public static String getGamePhaseAsText() {
    switch (gamePhase) {
    case PLAYER_ONE_PLAY_PAWN:
      return "Player One Play Pawn";
    case PLAYER_ONE_SELECT_PAWN:
      return "Player One Select Pawn";
    case PLAYER_TWO_PLAY_PAWN:
      return "Player Two Play Pawn";
    case PLAYER_TWO_SELECT_PAWN:
      return "Player Two Select Pawn";
    case PLAYER_ONE_WIN:
      return "Player One Win";
    case PLAYER_TWO_WIN:
      return "Player Two Win";
    default:
      return "Game Not Started";
    }
  }

}
