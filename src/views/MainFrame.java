package src.views;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.views.components.DialogPanel;
import src.views.components.EventsHandler;

public class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private DialogPanel dialog;

  public MainFrame() {
    setTitle("Quarto Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    // Créer les différentes interfaces
    mainMenu = new MainMenu();
    gameBoard = new GameBoard();

    // Ajouter les interfaces au conteneur principal
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
