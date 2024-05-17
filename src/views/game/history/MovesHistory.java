package src.views.game.history;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import src.model.QuartoHistory;
import src.views.listeners.GameStatusListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import java.awt.Font;

public class MovesHistory extends JScrollPane implements GameStatusListener {

    private JPanel movesContainer;
    private List<Move> moveComponents; // List to keep track of Move components
    private int maxDisplayedMoves = 10; // Maximum displayed moves

    public MovesHistory() {
        movesContainer = new JPanel();
        movesContainer.setLayout(new GridBagLayout());
        moveComponents = new ArrayList<>(); // Initialize the list

        GameStatusHandler.addGameStatusListener(this);

        // Add title label outside of the scroll pane
        JLabel titleLabel = new JLabel("Move History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        setColumnHeaderView(titleLabel);

        setViewportView(movesContainer);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public void addMove(String move) {
        Move newMove = new Move(move);
        moveComponents.add(0, newMove); // Add at the beginning of the list (to show newest moves first)
        updateMovesContainer();
    }

    public void clear() {
        moveComponents.clear(); // Clear the move components list
        updateMovesContainer();
    }

    private void updateMovesContainer() {
        movesContainer.removeAll();

        GridBagConstraints moveConstraints = new GridBagConstraints();
        moveConstraints.gridx = 0;
        moveConstraints.fill = GridBagConstraints.HORIZONTAL;
        moveConstraints.weightx = 1.0;

        // Add moves to container with separators in between
        for (int i = 0; i < moveComponents.size(); i++) {
            moveConstraints.gridy = 2 * i; // Move
            movesContainer.add(moveComponents.get(i), moveConstraints);

            // Add separator
            moveConstraints.gridy = 2 * i + 1; // Separator
            movesContainer.add(new JSeparator(JSeparator.HORIZONTAL), moveConstraints);
        }

        movesContainer.revalidate();
        movesContainer.repaint();
        // Scroll to the bottom
        getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
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
