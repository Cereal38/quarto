package src.views.utils;

import java.util.ArrayList;
import java.util.List;
import src.views.listeners.GameStatusListener;

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
  private static String selectedPawn = "";

  // The list of game status listeners
  private static final List<GameStatusListener> listeners = new ArrayList<>();

  /**
   * Adds a game status listener to the list of listeners.
   *
   * @param listener the game status listener to be added
   */
  public static void addGameStatusListener(GameStatusListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes a game status listener from the list of registered listeners.
   *
   * @param listener the game status listener to be removed
   */
  public static void removeGameStatusListener(GameStatusListener listener) {
    listeners.remove(listener);
  }

  public static void setGamePhase(int phase) {
    gamePhase = phase;
    informListeners();
  }

  public static void startGame() {
    gamePhase = PLAYER_ONE_SELECT_PAWN;
    informListeners();
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
    informListeners();
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

  public static void setSelectedPawn(String pawn) {
    selectedPawn = pawn;
  }

  public static String getSelectedPawn() {
    return selectedPawn;
  }

  public static void informListeners() {
    System.out.println("DEBUG - GameStatusHandler.informListeners()");
    for (GameStatusListener listener : listeners) {
      listener.update();
    }
  }

}
