package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.utils.EventsHandler;

public class TopBarGameBoard extends JPanel {

  public TopBarGameBoard(int width, int height) {

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(width, height));

    // Left panel with undo and redo buttons
    JPanel leftPanel = new LeftPanelTopBar();
    add(leftPanel, BorderLayout.WEST);

    // Right panel with pause button
    JPanel rightPanel = new RightPanelTopBar();
    add(rightPanel, BorderLayout.EAST);

    // Center panel with the current state of the game
    JPanel centerPanel = new CenterPanelTopBar();
    add(centerPanel, BorderLayout.CENTER);

  }

}
