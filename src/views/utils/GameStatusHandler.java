package src.views.utils;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import src.views.game.board.GameOverDialog;
import src.views.game.history.MovePanel;
import src.views.listeners.GameStatusListener;

public class GameStatusHandler {

  // The list of game status listeners
  private static final List<GameStatusListener> listeners = new ArrayList<>();

  // Keep the history of moves
  private static List<MovePanel> moveComponents = new ArrayList<>();

  private static boolean isPaused = false;

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
    isPaused = false;
    actionPerformed();
  }

  /**
   * If the current player is an AI, play a shot or select a pawn. Add 1 second
   * delay.
   */
  private static void aiPlay() {
    if (EventsHandler.getController().isCurrentPlayerAI()) {
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
    if (isPaused()) {
      EventsHandler.showSnackbar("game-paused");
      return;
    }
    if (!EventsHandler.getController().isSelectionPhase()) {
      EventsHandler.showSnackbar("cant-select-pawn");
      return;
    }
    if (EventsHandler.getController().isGameOver()) {
      return;
    }

    EventsHandler.getController().selectPawn(code);
    actionPerformed();
  }

  public static void playShot(int line, int column) {
    if (isPaused()) {
      EventsHandler.showSnackbar("game-paused");
      return;
    }
    if (!EventsHandler.getController().isPlayPhase()) {
      EventsHandler.showSnackbar("cant-play-pawn");
      return;
    }
    if (EventsHandler.getController().isGameOver()) {
      return;
    }
    EventsHandler.getController().playShot(line, column);
    // Display the shot on the board
    informListeners();
    // Check if the game is finished. If not, got to the next phase
    if (!checkGameOver()) {
      actionPerformed();
    }
  }

  /**
   * Checks if the game is finished. If it is, shows a dialog.
   * 
   * @return true if the game is finished, false otherwise
   */
  private static boolean checkGameOver() {
    if (EventsHandler.getController().isGameOver()) {
      // Set winner to null if it's a draw
      String winner;
      if (EventsHandler.getController().isGameWon()) {
        winner = EventsHandler.getController().getCurrentPlayerName();
        List<int[]> winLine = EventsHandler.getController().getWinLine();
        // Display the winning line
        for (int[] axis : winLine) {
          System.out.println("Winning line: " + axis[0] + " " + axis[1]);
        }
      } else {
        winner = null;
      }
      SwingUtilities.invokeLater(() -> EventsHandler.showDialog(new GameOverDialog(winner), false));
      // Pause the game
      pauseGame();
      return true;
    }
    return false;
  }

  public static void undo() {
    EventsHandler.getController().undo();
    pauseGame();
    actionPerformed();
  }

  public static void redo() {
    EventsHandler.getController().redo();
    pauseGame();
    actionPerformed();
  }

  public static void addMove(String move, ImageIcon icon) {
    MovePanel newMove = new MovePanel(move, icon);
    moveComponents.add(newMove);
  }

  public static void clearMoves() {
    moveComponents.clear();
  }

  public static List<MovePanel> getMoveComponents() {
    return moveComponents;
  }

  public static void pauseGame() {
    // Can't pause in PvP mode
    if (isPvP()) {
      return;
    }
    isPaused = true;
    informListeners();
  }

  public static void resumeGame() {
    // Can't resume if game is over
    if (EventsHandler.getController().isGameOver()) {
      return;
    }
    isPaused = false;
    actionPerformed();
  }

  public static boolean isPaused() {
    return isPaused;
  }

  /**
   * Return true if both players are humans
   * 
   * @return
   */
  public static boolean isPvP() {
    return !EventsHandler.getController().isPlayer1AI() && !EventsHandler.getController().isPlayer2AI();
  }

}
