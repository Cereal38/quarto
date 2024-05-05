package src.views.GameBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.TranslatedButton;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;

public class TopBarGameBoard extends JPanel {

  private TranslatedButton btnUndo = new TranslatedButton("undo");
  private TranslatedButton btnRedo = new TranslatedButton("redo");
  private TranslatedButton btnPause = new TranslatedButton("pause");
  private TranslatedLabel lblTurnOf = new TranslatedLabel("turn-of-player");
  private JLabel lblPlayer = new JLabel("Player 1");

  public TopBarGameBoard() {
    setLayout(new BorderLayout());

    // Add action listeners to the buttons
    btnPause.addActionListener(e -> {
      EventsHandler.showDialog(new PauseDialogContent());
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
    lblPlayer.setForeground(Color.BLUE);
    centerPanel.add(lblTurnOf);
    centerPanel.add(lblPlayer);
  }

}
