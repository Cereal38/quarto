package src.views.game.history;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

public class MovesHistory extends JScrollPane {

    private JPanel movesContainer;

    public MovesHistory() {
        // Set a layout for the moves in the history
        movesContainer = new JPanel();
        movesContainer.setLayout(new GridLayout(0, 1));

        // Add the moves to the history
        for (int i = 0; i < 10; i++) {
            Move move = new Move("Move " + i);
            movesContainer.add(move);
        }

        // Set the preferred size of the moves container
        movesContainer.setPreferredSize(new Dimension(200, 600));

        // Make the container scrollable
        setViewportView(movesContainer);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
}