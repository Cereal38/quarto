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

  public static int getGamePhase() {
    return gamePhase;
  }

}
