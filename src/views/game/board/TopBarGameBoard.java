package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import src.views.utils.EventsHandler;

public class TopBarGameBoard extends JPanel {

  public TopBarGameBoard(int width, int height) {

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(width, height));

    // Create elements
    PauseMenuButton btnPause = new PauseMenuButton();

    EventsHandler.setPauseMenuButton(btnPause);

    // Add action listeners to the buttons
    btnPause.addActionListener(e -> {
      EventsHandler.showDialog(new PauseDialogContent(), true);
    });

    // Left panel with undo and redo buttons
    JPanel leftPanel = new LeftPanelTopBar();
    add(leftPanel, BorderLayout.WEST);

    // Right panel with pause button
    JPanel rightPanel = new JPanel();
    add(rightPanel, BorderLayout.EAST);
    rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 10));
    rightPanel.add(btnPause);

    // Center panel with the current state of the game
    JPanel centerPanel = new CenterPanelTopBar();
    add(centerPanel, BorderLayout.CENTER);

  }

}
