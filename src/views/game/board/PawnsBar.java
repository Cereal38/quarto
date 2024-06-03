package src.views.game.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.EventsHandler;

/**
 * Represents the panel that displays the available pawns.
 */

public class PawnsBar extends JPanel {

  /**
   * Constructs a PawnsBar with the specified width and height.
   *
   * @param width  The width of the pawns bar.
   * @param height The height of the pawns bar.
   */
  public PawnsBar(int width, int height) {
    setLayout(new GridLayout(8, 2));

    setPreferredSize(new Dimension(width, height));
    setBackground(new Color(211, 165, 71)); // Add background color to avoid flickering

    // Get the available pawns
    Pawn[] pawns = EventsHandler.getController().getAvailablePawns();

    // Display the pawns
    // TODO: Rework this
    int i = 0;
    for (Pawn pawn : pawns) {
      add(new PawnsBarSlot(pawn, i % 2));
      i++;
    }

  }
}
