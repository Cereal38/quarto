package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;

public class TopBarGameBoard extends JPanel {

  public TopBarGameBoard(int width, int height) {

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(width, height));

    // Create elements
    UndoButton btnUndo = new UndoButton();
    RedoButton btnRedo = new RedoButton();
    PauseMenuButton btnPause = new PauseMenuButton();
    TranslatedLabel stateLbl = new TranslatedLabel("");
    JLabel playerLbl = new JLabel();

    // Set the labels
    if (EventsHandler.getController().isSelectionPhase()) {
      stateLbl.setKey("select-pawn");
    } else if (EventsHandler.getController().isPlayPhase()) {
      stateLbl.setKey("play-pawn");
    }
    playerLbl.setText(EventsHandler.getController().getCurrentPlayerName());

    EventsHandler.setUndoButton(btnUndo);
    EventsHandler.setRedoButton(btnRedo);
    EventsHandler.setPauseMenuButton(btnPause);

    // Add action listeners to the buttons
    btnPause.addActionListener(e -> {
      EventsHandler.showDialog(new PauseDialogContent(), true);
    });

    // Left panel with undo and redo buttons
    JPanel leftPanel = new JPanel();
    add(leftPanel, BorderLayout.WEST);
    leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
    leftPanel.add(btnUndo);
    leftPanel.add(btnRedo);

    // Right panel with pause button
    JPanel rightPanel = new JPanel();
    add(rightPanel, BorderLayout.EAST);
    rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 10));
    rightPanel.add(btnPause);

    // Center panel with the current state of the game
    JPanel centerPanel = new JPanel();
    add(centerPanel, BorderLayout.CENTER);
    centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 15));
    playerLbl.setForeground(Color.BLUE);
    centerPanel.add(playerLbl);
    centerPanel.add(stateLbl);
  }

}
