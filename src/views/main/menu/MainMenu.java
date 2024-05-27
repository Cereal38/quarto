package src.views.main.menu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.components.GridCenterPanel;
import src.views.utils.EventsHandler;

public class MainMenu extends JPanel {
  private CustomizedButton btnNewGame = new CustomizedButton("new-game");
  private CustomizedButton btnLoad = new CustomizedButton("load");
    private Image backgroundImage;
    private static int theme = 0; // 0: light, 1: dark


  public MainMenu() {
    setLayout(new BorderLayout());

    theme = EventsHandler.getTheme();

    if (theme == 1) {
        backgroundImage = new javax.swing.ImageIcon(getClass().getResource("/assets/images/MenuBgDark.jpg")).getImage();
    }
    else {
        backgroundImage = new javax.swing.ImageIcon(getClass().getResource("/assets/images/MenuBg.jpg")).getImage();
    }

    JPanel navbar = new TopBarMainMenu();
    add(navbar, BorderLayout.NORTH);
    navbar.setOpaque(false);

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
    gridCenter.setOpaque(false);
    // Display menu items as a list
    menu.setLayout(new GridLayout(4, 1, 0, 5));
    menu.setOpaque(false);
    JLabel titleLabel = new JLabel("Quarto");
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    menu.add(titleLabel);
    menu.add(btnNewGame);
    menu.add(btnLoad);


  }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
