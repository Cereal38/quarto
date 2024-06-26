package src.views.main.menu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.components.GridCenterPanel;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

/**
 * The MainMenu class represents the main menu of the game application. It
 * provides options for starting a new game and loading a saved game. This menu
 * is displayed when the game application starts.
 *
 * <p>
 * The MainMenu class extends the JPanel class and implements the ThemeListener
 * interface to listen for theme change events.
 * </p>
 */

public class MainMenu extends JPanel implements ThemeListener {
  private CustomizedButton btnNewGame = new CustomizedButton("new-game");
  private CustomizedButton btnLoad = new CustomizedButton("load");
  private ImageThemed backgroundImage = new ImageThemed("main-menu.jpg");
  private ImageThemed logoImage = new ImageThemed("quarto.png");
  private JLabel logo;

  /**
   * Constructs a MainMenu object.
   */
  public MainMenu() {
    ThemeUtils.addThemeListener(this);
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

    // Disable load button if there are no saved games
    checkLoadButton();

    logoImage.setSize(200, 45);
    logo = new JLabel(new ImageIcon(logoImage.getImage()));
    menu.add(logo);
    menu.add(new JLabel()); // Divider
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
    logo.setIcon(new ImageIcon(logoImage.getImage()));
    repaint();
  }

  /**
   * Checks if the load button should be enabled or disabled.
   */
  public void checkLoadButton() {
    if (EventsHandler.getController().getSlotFiles().isEmpty()) {
      btnLoad.setEnabled(false);
    } else {
      btnLoad.setEnabled(true);
    }
  }
}
