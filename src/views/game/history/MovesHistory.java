package src.views.game.history;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

public class MovesHistory extends JScrollPane {

    private JPanel movesContainer;

    public MovesHistory() {
        // Set a layout for the moves in the history
        movesContainer = new JPanel();
        movesContainer.setLayout(new GridLayout(0, 1));

        JButton button = new JButton("Add Move");

        // Add the button to the history
        movesContainer.add(button);

        // make the button add a move to the history on the top of the list
        button.addActionListener(e -> {
            addMove("New Move");
        });

        // Add the moves to the history
        for (int i = 0; i < 20; i++) {
            Move move = new Move("Move " + i);
            movesContainer.add(move);
        }

        // Set the preferred size of the moves container
        movesContainer.setPreferredSize(new Dimension(250, 1000));

        // Make the container scrollable
        setViewportView(movesContainer);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    // Add a move to the history on the top of the list
    public void addMove(String move) {
        Move newMove = new Move(move);
        movesContainer.add(newMove, 1);
        movesContainer.revalidate();
        movesContainer.repaint();
    }
}