package src.views.gameboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.PauseMenuButton;
import src.views.components.RedoButton;
import src.views.components.TranslatedButton;
import src.views.components.TranslatedLabel;
import src.views.components.UndoButton;
import src.views.utils.EventsHandler;

public class TopBarGameBoard extends JPanel {
    private UndoButton btnUndo = new UndoButton();
    private RedoButton btnRedo = new RedoButton();
    private PauseMenuButton btnPause = new PauseMenuButton();
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
