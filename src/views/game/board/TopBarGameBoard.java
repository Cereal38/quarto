package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.PauseMenuButton;
import src.views.components.RedoButton;
import src.views.components.TranslatedLabel;
import src.views.components.UndoButton;
import src.views.listeners.GameStatusListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class TopBarGameBoard extends JPanel implements GameStatusListener {
    private UndoButton btnUndo = new UndoButton();
    private RedoButton btnRedo = new RedoButton();
    private PauseMenuButton btnPause = new PauseMenuButton();
    private TranslatedLabel stateLbl = new TranslatedLabel("");
    private JLabel playerLbl = new JLabel();

    public TopBarGameBoard() {
        setLayout(new BorderLayout());

        EventsHandler.setUndoButton(btnUndo);
        EventsHandler.setRedoButton(btnRedo);
        EventsHandler.setPauseMenuButton(btnPause);

        // Register this class as a game status listener
        GameStatusHandler.addGameStatusListener(this);

        // Add action listeners to the buttons
        btnPause.addActionListener(e -> {
            EventsHandler.showDialog(new PauseDialogContent());
        });

        // Set the text of the labels
        upatePlayerLbl();

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

    private void upatePlayerLbl() {

        System.out.println(GameStatusHandler.getGamePhaseAsText());

        if (GameStatusHandler.isPlayerOneTurn()) {
            playerLbl.setText(GameStatusHandler.getPlayer1Name());
        } else if (GameStatusHandler.isPlayerTwoTurn()) {
            playerLbl.setText(GameStatusHandler.getPlayer2Name());
        } else {
            playerLbl.setText("");
        }
    }

    /**
     * Updates the state of the game board. This method is called to update the UI
     * components of the game board based on the current game status. It's called
     * when the game status is updated.
     */
    @Override
    public void update() {
        upatePlayerLbl();
        if (GameStatusHandler.isSelectionPhase()) {
            stateLbl.setKey("select-pawn");
        } else if (GameStatusHandler.isPlayPhase()) {
            stateLbl.setKey("play-pawn");
        } else {
            stateLbl.setKey("");
        }
    }

}
