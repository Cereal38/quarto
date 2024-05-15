package src.views.utils;

import java.util.ArrayList;
import java.util.List;
import src.views.game.board.GameOverDialog;
import src.views.listeners.GameStatusListener;

public class GameStatusHandler {

  // The list of game status listeners
  private static final List<GameStatusListener> listeners = new ArrayList<>();

  public static void startGame() {
    informListeners();
  }

  // ================== Game Status Listeners ==================

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

  public static void informListeners() {
    for (GameStatusListener listener : listeners) {
      listener.update();
    }
  }

  public static void selectPawn(String code) {
    EventsHandler.getController().selectPawn(code);
    informListeners();
  }

  /**
   * If the current player is an AI, play a shot or select a pawn. Add 1 second
   * delay.
   */
  private static void aiPlay() {
    if (EventsHandler.getController().isCurrentPlayerAI()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // We call methods with bullshit data because it's decided by the AI in the
      // model
      if (EventsHandler.getController().isPlayPhase()) {
        EventsHandler.getController().playShot(0, 0);
      } else {
        EventsHandler.getController().selectPawn("0000");
      }
      informListeners();
    }
  }

  public static void playShotPlayer(int line, int column) {
    EventsHandler.getController().playShot(line, column);
    informListeners();
    // Check if the game is finished. If so, show a dialog
    if (EventsHandler.getController().isGameFinished(line, column)) {
      EventsHandler.showDialog(new GameOverDialog("Player name"), false);
    } else {
      aiPlay();
    }
  }

  public static void undo() {
    EventsHandler.getController().undo();
    informListeners();
    aiPlay();
  }

  public static void redo() {
    EventsHandler.getController().redo();
    informListeners();
    aiPlay();
  }

}
