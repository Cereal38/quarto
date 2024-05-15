package src.views.game.history;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import src.model.QuartoHistory;
import src.views.utils.EventsHandler;
import java.awt.GridLayout;

public class MovesHistory extends JScrollPane {

    private JPanel movesContainer;
    JButton button = new JButton("Add Move");

    public MovesHistory() {
        // Set a layout for the moves in the history
        movesContainer = new JPanel();
        movesContainer.setLayout(new GridLayout(0, 1));

        // make it small
        button.setPreferredSize(new Dimension(250, 50));

        // Add the button to the history
        movesContainer.add(button);

        // make the button add a move to the history on the top of the list
        button.addActionListener(e -> {
            QuartoHistory save = EventsHandler.getController().getModel().getCurrentState();

            // clear the history
            clear();

            while (save.getPrevious() != null) {

                String name = save.getName();
                int pawn = save.getIndexPawn();
                int x = save.getLine();
                int y = save.getColumn();

                if (save.getIndexPawn() != 0) {
                    String selected = name + " selected the pawn " + pawn;
                    addMove(selected);
                } else {
                    String placed = name + " placed the pawn " + " at " + x + " " + y;
                    addMove(placed);
                }

                save = save.getPrevious();
            }
        });

        // Set the preferred size of the moves container
        movesContainer.setPreferredSize(new Dimension(250, 1000));

        // Make the container scrollable
        setViewportView(movesContainer);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    // Add a move to the history on the top of the list
    public void addMove(String move) {
        Move newMove = new Move(move);
        movesContainer.add(newMove);
        movesContainer.revalidate();
        movesContainer.repaint();
    }

    // Clear the history moves and not the button
    public void clear() {
        movesContainer.removeAll();
        movesContainer.add(button);
        movesContainer.revalidate();
        movesContainer.repaint();
    }
}