package src.views.main.menu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.components.GridCenterPanel;
import src.views.utils.EventsHandler;

public class MainMenu extends JPanel {
  private CustomizedButton btnNewGame = new CustomizedButton("new-game");
  private CustomizedButton btnLoad = new CustomizedButton("load");

  public MainMenu() {
    setLayout(new BorderLayout());

    JPanel navbar = new TopBarMainMenu();
    add(navbar, BorderLayout.NORTH);

    // Add action listeners to the buttons
    btnNewGame.addActionListener(e -> {
      EventsHandler.navigate("ChoosePlayers");
    });
    btnLoad.addActionListener(e -> {
      EventsHandler.navigate("LoadPage");
    });

    // Menu centered on the screen
    // The menu is at the middle of a 3x3 grid
    JPanel menu = new JPanel();
    JPanel gridCenter = new GridCenterPanel(menu);
    add(gridCenter, BorderLayout.CENTER);

    // Display menu items as a list
    menu.setLayout(new GridLayout(4, 1, 0, 5));

    JLabel titleLabel = new JLabel("Quarto");
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    menu.add(titleLabel);
    menu.add(btnNewGame);
    menu.add(btnLoad);

  }

}
