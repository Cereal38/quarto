package src.views.game.history;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import src.model.QuartoHistory;
import src.views.listeners.GameStatusListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class MovesHistory extends JScrollPane implements GameStatusListener {

  private JPanel movesContainer;
  private List<Move> moveComponents; // List to keep track of Move components
  private int maxDisplayedMoves = 10; // Maximum displayed moves

  public MovesHistory() {
    // Set a layout for the moves in the history
    movesContainer = new JPanel();
    movesContainer.setLayout(new GridLayout(0, 1));
    moveComponents = new ArrayList<>(); // Initialize the list

    GameStatusHandler.addGameStatusListener(this);

    movesContainer.setPreferredSize(new Dimension(250, maxDisplayedMoves * 50)); // Adjusted based on
                                                                                 // maxDisplayedMoves

    setViewportView(movesContainer);
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  }

  // Add a move to the history on the top of the list
  public void addMove(String move) {
    Move newMove;
    if (moveComponents.size() < maxDisplayedMoves) {
      newMove = new Move(move);
      movesContainer.add(newMove);
      moveComponents.add(newMove);
    } else {
      newMove = moveComponents.remove(0);
      newMove.updateMoveText(move); // Update the text
      moveComponents.add(newMove);
    }
    movesContainer.revalidate();
    movesContainer.repaint();
  }

  public void clear() {
    movesContainer.removeAll();
    moveComponents.clear(); // Clear the move components list
    movesContainer.revalidate();
    movesContainer.repaint();
  }

  public void updateHistory() {
    QuartoHistory save = EventsHandler.getController().getModel().getCurrentState();

    clear();

    boolean isFirstMove = true; // Flag to check if it's the first move

    while (save != null) {
      String name = save.getName();
      int pawn = save.getIndexPawn();
      int x = save.getLine();
      int y = save.getColumn();

      // Skip adding the first move if it's null
      if (isFirstMove && name == null && pawn == 0 && x == 0 && y == 0) {
        save = save.getPrevious();
        isFirstMove = false;
        continue;
      }

      String moveDescription;
      if (pawn != 0) {
        moveDescription = name + " selected the pawn " + pawn;
      } else {
        moveDescription = name + " placed the pawn at " + x + " " + y;
      }
      addMove(moveDescription);

      save = save.getPrevious();
    }
  }

  @Override
  public void update() {
    clear();
    updateHistory();
  }
}