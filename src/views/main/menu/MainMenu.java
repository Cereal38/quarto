package src.views.main.menu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.components.GridCenterPanel;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class MainMenu extends JPanel implements ThemeListener {
  private CustomizedButton btnNewGame = new CustomizedButton("new-game");
  private CustomizedButton btnLoad = new CustomizedButton("load");
  private ImageThemed backgroundImage = new ImageThemed("main-menu.jpg");

  public MainMenu() {
    ThemeUtils.addLanguageChangeListener(this);
    EventsHandler.setMainMenu(this);

    setLayout(new BorderLayout());

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
    g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }
}
