package src.views;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private PauseMenu pauseMenu;

  public MainFrame() {
    setTitle("Quarto Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    // Créer les différentes interfaces
    mainMenu = new MainMenu(this);
    gameBoard = new GameBoard(this);
    pauseMenu = new PauseMenu(this);

    // Ajouter les interfaces au conteneur principal avec un identifiant unique
    mainPanel.add(mainMenu, "MainMenu");
    mainPanel.add(gameBoard, "GameBoard");
    mainPanel.add(pauseMenu, "PauseMenu");

    // Ajouter le panneau principal à la JFrame
    add(mainPanel);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Gestion des actions des différents boutons
    String command = e.getActionCommand();

    switch (command) {
      case "Player vs Player":
        cardLayout.show(mainPanel, "GameBoard"); // Passer au plateau de jeu
        break;
      case "Pause Menu":
        cardLayout.show(mainPanel, "PauseMenu"); // Afficher le menu de pause
        break;
      case "Main Menu":
        cardLayout.show(mainPanel, "MainMenu"); // Revenir au menu principal
        break;
      case "Quit":
        System.exit(0); // Quitter le jeu
        break;
      // Ajouter d'autres cas pour gérer d'autres boutons
    }
  }
}
