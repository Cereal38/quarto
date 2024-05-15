package src.views.utils;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import src.views.game.board.GameOverDialog;
import src.views.listeners.GameStatusListener;

public class GameStatusHandler {

  // The list of game status listeners
  private static final List<GameStatusListener> listeners = new ArrayList<>();

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

  public static void startGame() {
    actionPerformed();
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
        playShot(0, 0);
      } else {
        selectPawn("0000");
      }
    }
  }

  /**
   * Everytime an action is performed, we inform the listeners and check if the AI
   * needs to take an action.
   */
  private static void actionPerformed() {
    informListeners();
    // Using invokeLater to let the UI update before the AI plays
    SwingUtilities.invokeLater(() -> aiPlay());
  }

  public static void selectPawn(String code) {
    EventsHandler.getController().selectPawn(code);
    actionPerformed();
  }

  public static void playShot(int line, int column) {
    EventsHandler.getController().playShot(line, column);
    // Check if the game is finished. If so, show a dialog
    if (EventsHandler.getController().isGameFinished(line, column)) {
      EventsHandler.showDialog(new GameOverDialog("Player name"), false);
    } else {
      actionPerformed();
    }
  }

  public static void undo() {
    EventsHandler.getController().undo();
    actionPerformed();
  }

  public static void redo() {
    EventsHandler.getController().redo();
    actionPerformed();
  }

}
