package src.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.views.components.DialogPanel;
import src.views.gameboard.GameBoard;
import src.views.mainmenu.MainMenu;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;

public class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private DialogPanel dialog;

  public MainFrame() {
    setTitle("Quarto Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // set min size 1000x500
    setMinimumSize(new Dimension(1000, 500));
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    // Create app pages
    mainMenu = new MainMenu();

    // Setup DimensionUtils
    DimensionUtils.setMainFrame(this);

    gameBoard = new GameBoard();

    // Add pages to the main panel
    mainPanel.add(mainMenu, "MainMenu");
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
