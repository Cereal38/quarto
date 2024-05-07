package src.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.views.components.DialogPanel;
import src.views.game.board.GameBoard;
import src.views.main.menu.MainMenu;
import src.views.players.names.ChoosePlayers;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;

public class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private ChoosePlayers choosePlayers;
  private DialogPanel dialog;

  public MainFrame() {
    setTitle("Quarto Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(1000, 800));
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    choosePlayers = new ChoosePlayers();

    // Create app pages
    mainMenu = new MainMenu();

    // Setup DimensionUtils
    DimensionUtils.setMainFrame(this);

    gameBoard = new GameBoard();

    // Add pages to the main panel
    mainPanel.add(mainMenu, "MainMenu");
    mainPanel.add(choosePlayers, "ChoosePlayers");
    mainPanel.add(gameBoard, "GameBoard");

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