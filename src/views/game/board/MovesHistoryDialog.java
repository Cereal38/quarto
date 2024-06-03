package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import src.views.game.history.MovesHistory;

/**
 * Represents a dialog panel for displaying the moves history.
 */
public class MovesHistoryDialog extends JPanel {

  /**
   * Constructs a MovesHistoryDialog object.
   */
  public MovesHistoryDialog() {
    setLayout(new BorderLayout());

    // Create an instance of MovesHistory
    MovesHistory movesHistory = new MovesHistory();

    // Add MovesHistory to the dialog
    add(movesHistory, BorderLayout.CENTER);

    // Set the size and other properties of the dialog
    setPreferredSize(new Dimension(400, 600));
  }
}
