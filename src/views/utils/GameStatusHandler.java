package src.views.utils;

public class GameStatusHandler {

  private static int turnOf = -1; // 0: player 1, 1: player 2, -1: null

  public static int getTurnOf() {
    return turnOf;
  }

  public static void setTurnOf(int turnOf) {
    GameStatusHandler.turnOf = turnOf;
  }

}
