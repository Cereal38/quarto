package src.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import src.views.components.DialogPanel;

public class MainFrame extends JFrame implements ActionListener {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private MainMenu mainMenu;
  private GameBoard gameBoard;
  private boolean isDarkTheme = false;
  private DialogPanel dialog;

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

    // Ajouter les interfaces au conteneur principal
    mainPanel.add(mainMenu, "MainMenu");
    mainPanel.add(gameBoard, "GameBoard");

    add(mainPanel);

    // Init the dialog panel (Multi-purpose dialog panel)
    dialog = new DialogPanel(this);
    dialog.setVisible(false);

    getLayeredPane().add(dialog, -1);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    switch (command) {
    case "Player vs Player":
      cardLayout.show(mainPanel, "GameBoard");
      break;
    case "Pause Menu":
      dialog.setContent(new PauseDialogContent(this));
      break;
    case "Main Menu":
      dialog.setVisible(false);
      cardLayout.show(mainPanel, "MainMenu");
      break;
    case "Music":
      JPanel musicDialog = new JPanel();
      JLabel musicLabel = new JLabel("Music");
      musicDialog.add(musicLabel);
      dialog.setContent(musicDialog);
      break;
    case "Manual":
      JPanel manualDialog = new JPanel();
      JLabel manualLabel = new JLabel("Manual");
      manualDialog.add(manualLabel);
      dialog.setContent(manualDialog);
      break;
    case "Quit":
      System.exit(0);
      break;
    case "DarkMode":
      toggleTheme();
      break;
    case "Language":
      break;
    default:
      break;
    }
  }

  private void toggleTheme() {
    if (isDarkTheme) {
      applyLightTheme(); // Applique le thème clair
      isDarkTheme = false;
      System.out.println("Light theme applied");
    } else {
      applyDarkTheme(); // Applique le thème sombre
      isDarkTheme = true;
      System.out.println("Dark theme applied");
    }

    // Met à jour l'interface après le changement de thème
    SwingUtilities.updateComponentTreeUI(this);
  }

  private void applyLightTheme() {
    UIManager.put("Panel.background", Color.WHITE);
    UIManager.put("Label.foreground", Color.BLACK);
    UIManager.put("Button.background", Color.WHITE);
    UIManager.put("Button.foreground", Color.BLACK);
    UIManager.put("Button.border", BorderFactory.createLineBorder(Color.GRAY));
    // Ajuster d'autres propriétés pour le thème clair
  }

  private void applyDarkTheme() {
    UIManager.put("Panel.background", Color.DARK_GRAY);
    UIManager.put("Label.foreground", Color.WHITE);
    UIManager.put("Button.background", Color.DARK_GRAY);
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("Button.border", BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    // Ajuster d'autres propriétés pour le thème sombre
  }

}
