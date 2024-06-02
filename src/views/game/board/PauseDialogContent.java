package src.views.game.board;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.rules.RulesPage;
import src.views.utils.EventsHandler;

public class PauseDialogContent extends JPanel {
  private static final int MARGIN = 20;

  private CustomizedButton btnSave = new CustomizedButton("save");
  private CustomizedButton btnRestart = new CustomizedButton("restart");
  private CustomizedButton btnRules = new CustomizedButton("rules");
  private CustomizedButton btnMainMenu = new CustomizedButton("main-menu");
  private Image bgImage;

  public PauseDialogContent() {
    setLayout(new GridLayout(0, 1, 0, 10));
    setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

    // Add action listeners to the buttons
    btnMainMenu.addActionListener(e -> {
      // Navigate to the main menu
      EventsHandler.navigate("MainMenu");
      EventsHandler.hideDialog();
    });

    btnSave.addActionListener(e -> {
      EventsHandler.navigate("SavePage");
      EventsHandler.hideDialog();
    });

    btnRules.addActionListener(e -> {
      EventsHandler.hideDialog();
      EventsHandler.showDialog(new RulesPage(false), true);
    });

    add(btnSave);
    add(btnRestart);
    add(btnRules);
    add(btnMainMenu);

    try {
      bgImage = ImageIO.read(new File("assets/images/squared-background.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}