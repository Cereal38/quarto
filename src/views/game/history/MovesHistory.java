package src.views.game.history;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import src.model.QuartoHistory;
import src.views.listeners.GameStatusListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class MovesHistory extends JScrollPane implements GameStatusListener {

  private JPanel movesContainer;
  private int maxDisplayedMoves = 10; // Maximum displayed moves

  public MovesHistory() {
    movesContainer = new JPanel();
    movesContainer.setLayout(new GridBagLayout());

    GameStatusHandler.addGameStatusListener(this);

    // Add title label outside of the scroll pane
    JLabel titleLabel = new JLabel("Move History");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    setColumnHeaderView(titleLabel);

    setViewportView(movesContainer);
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  }

  public void addMove(String move) {
    GameStatusHandler.addMove(move);
    updateMovesContainer();
  }

  public void clear() {
    GameStatusHandler.clearMoves();
    updateMovesContainer();
  }

  private void updateMovesContainer() {
    movesContainer.removeAll();

    GridBagConstraints moveConstraints = new GridBagConstraints();
    moveConstraints.gridx = 0;
    moveConstraints.fill = GridBagConstraints.HORIZONTAL;
    moveConstraints.weightx = 1.0;

    int moveNumber = 1; // Start with 1 for the most recent move

    // Add moves to container with separators in between
    for (int i = GameStatusHandler.getMoveComponents().size() - 1; i >= 0; i--) {
      moveConstraints.gridy = 2 * i; // Move
      // Add move number label
      JLabel moveNumberLabel = new JLabel(moveNumber + " .");
      movesContainer.add(moveNumberLabel, moveConstraints);
      moveConstraints.gridx = 1;
      movesContainer.add(GameStatusHandler.getMoveComponents().get(i), moveConstraints);
      moveConstraints.gridx = 0;

      // Add separator that spans across both columns
      moveConstraints.gridy = 2 * (GameStatusHandler.getMoveComponents().size() - 1 - i) + 1; // Separator
      moveConstraints.gridwidth = 2; // Span across two columns
      movesContainer.add(new JSeparator(JSeparator.HORIZONTAL), moveConstraints);
      moveConstraints.gridwidth = 1; // Reset grid width
      // set color of the separator to black
      movesContainer.getComponent(movesContainer.getComponentCount() - 1).setForeground(java.awt.Color.BLACK);
      moveNumber++; // Increment the move number for the next iteration
    }

    movesContainer.revalidate();
    movesContainer.repaint();
    // Scroll to the top
    getVerticalScrollBar().setValue(0);
  }

  public void updateHistory() {
    QuartoHistory save = EventsHandler.getController().getModel().getCurrentState();

    while (save != null) {
      String name = save.getName();
      int pawn = save.getIndexPawn();
      int x = save.getLine();
      int y = save.getColumn();

      String moveDescription;
      if (name != null || pawn != 0 || (x != 0 && y != 0)) {
        if (pawn != 0) {
          moveDescription = name + " selected the pawn " + pawn;
        } else {
          moveDescription = name + " placed the pawn at " + x + " " + y;
        }
        // Add the move to the history at the top
        addMove(moveDescription);
      }

      save = save.getPrevious();
    }
  }

  @Override
  public void update() {
    clear();
    updateHistory();
  }
}
