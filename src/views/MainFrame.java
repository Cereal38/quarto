package src.views;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.views.components.DialogPanel;
import src.views.game.board.GameBoard;
import src.views.main.menu.MainMenu;
//import src.views.players.names.ChoosePlayers;
import src.views.players.names.ChoosePlayers;
import src.views.utils.EventsHandler;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MainMenu mainMenu;
    private GameBoard gameBoard;
    private DialogPanel dialog;
    private ChoosePlayers choosePlayers;

    public MainFrame() {
        setTitle("Quarto Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create app pages
        mainMenu = new MainMenu();
        gameBoard = new GameBoard();
        choosePlayers = new ChoosePlayers();


        // Add pages to the main panel
        mainPanel.add(mainMenu, "MainMenu");
        //mainPanel.add()
        mainPanel.add(gameBoard, "GameBoard");

        mainPanel.add(choosePlayers, "ChoosePlayers");

        add(mainPanel);

        // Init the dialog panel (Multi-purpose dialog panel)
        dialog = new DialogPanel(this);
        dialog.setVisible(false);
        getLayeredPane().add(dialog, -1);

        // Setup the EventsHandler
        EventsHandler.setCardLayout(cardLayout);
        EventsHandler.setMainPanel(mainPanel);
        EventsHandler.setDialog(dialog);

        setVisible(true);
    }

}
