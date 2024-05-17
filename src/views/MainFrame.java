package src.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.views.components.DialogPanel;
import src.views.game.board.GameBoard;
import src.views.load.save.LoadSavePage;
import src.views.main.menu.MainMenu;
import src.views.players.names.ChoosePlayers;
import src.views.rules.RulesPage;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;
import src.views.utils.PawnUtils;

public class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private ChoosePlayers choosePlayers;
  private DialogPanel dialog;
  private DialogPanel rules;
  private DialogPanel about;
  private LoadSavePage loadPage;
  private LoadSavePage savePage;
  private RulesPage rulesPage;
  private RulesPage aboutPage;

  public MainFrame() {
    setTitle("Quarto Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(1000, 800));
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    choosePlayers = new ChoosePlayers();
    loadPage = new LoadSavePage(false);
    savePage = new LoadSavePage(true);

    // Create app pages
    mainMenu = new MainMenu();

    // Setup DimensionUtils
    DimensionUtils.setMainFrame(this);

    gameBoard = new GameBoard();

    // Add pages to the main panel
    mainPanel.add(mainMenu, "MainMenu");
    mainPanel.add(choosePlayers, "ChoosePlayers");
    mainPanel.add(gameBoard, "GameBoard");
    mainPanel.add(loadPage, "LoadPage");
    mainPanel.add(savePage, "SavePage");

    add(mainPanel);

    // Init the dialog panel (Multi-purpose dialog panel)
    dialog = new DialogPanel(this);
    rules = new DialogPanel(this);
    about = new DialogPanel(this);
    dialog.setVisible(false);
    rules.setVisible(false);
    about.setVisible(false);
    getLayeredPane().add(dialog, -1);
    getLayeredPane().add(rules, -1);
    getLayeredPane().add(about, -1);

    // Setup the EventsHandler
    EventsHandler.setCardLayout(cardLayout);
    EventsHandler.setMainPanel(mainPanel);
    EventsHandler.setDialog(dialog);
    EventsHandler.setDialog(rules);
    EventsHandler.setDialog(about);

    // Load all pawns
    PawnUtils.initPawns();

    setVisible(true);
  }

}