package src.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.views.components.DialogPanel;
import src.views.components.SnackbarPanel;
import src.views.game.board.GameBoard;
import src.views.load.save.LoadPage;
import src.views.load.save.SavePage;
import src.views.main.menu.MainMenu;
import src.views.players.names.ChoosePlayers;
import src.views.rules.RulesPage;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;
import src.views.utils.ImageLibrary;
import src.views.utils.PawnUtils;

public class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private ChoosePlayers choosePlayers;
  private SnackbarPanel snackbar;
  private DialogPanel dialog;
  private DialogPanel rules;
  private DialogPanel about;
  private static LoadPage loadPage;
  private SavePage savePage;
  private RulesPage rulesPage;
  private RulesPage aboutPage;
  private DialogPanel save;

  public MainFrame() {
    setTitle("Quarto Game");
    setMinimumSize(new Dimension(1000, 800));
    setLocationRelativeTo(null);

    // Show a confirmation dialog when the user tries to close the window
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        EventsHandler.closeApp();
      }
    });

    // Load all images
    ImageLibrary.loadImages();

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    choosePlayers = new ChoosePlayers();
    loadPage = new LoadPage();
    savePage = new SavePage(loadPage);

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

    // Init the snackbar
    snackbar = new SnackbarPanel(this);
    snackbar.setVisible(false);
    getLayeredPane().add(snackbar, 0);

    // Init the dialog panel (Multi-purpose dialog panel)
    dialog = new DialogPanel(this);
    rules = new DialogPanel(this);
    about = new DialogPanel(this);
    save = new DialogPanel(this);
    dialog.setVisible(false);
    rules.setVisible(false);
    about.setVisible(false);
    save.setVisible(false);
    getLayeredPane().add(dialog, -1);
    getLayeredPane().add(rules, -1);
    getLayeredPane().add(about, -1);
    getLayeredPane().add(save, -1);

    // Setup the EventsHandler
    EventsHandler.setCardLayout(cardLayout);
    EventsHandler.setMainPanel(mainPanel);
    EventsHandler.setSnackbar(snackbar);
    EventsHandler.setDialog(dialog);
    EventsHandler.setDialog(rules);
    EventsHandler.setDialog(about);
    EventsHandler.setDialog(save);

    // Load all pawns
    PawnUtils.initPawns();

    setVisible(true);
  }

  public static LoadPage getLoadPage() {
    return loadPage;
  }

}