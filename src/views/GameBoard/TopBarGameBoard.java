package src.views.GameBoard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import src.views.components.TranslatedButton;
import src.views.utils.EventsHandler;

public class TopBarGameBoard extends JPanel {

  private TranslatedButton btnUndo = new TranslatedButton("undo");
  private TranslatedButton btnRedo = new TranslatedButton("redo");
  private TranslatedButton btnPause = new TranslatedButton("pause");

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
  }

}
