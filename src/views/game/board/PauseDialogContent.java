package src.views.game.board;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.views.MainFrame;
import src.views.components.CustomizedButton;
import src.views.components.ImageThemed;
import src.views.listeners.ThemeListener;
import src.views.load.save.SavePage;
import src.views.rules.RulesPage;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents the content of the pause dialog.
 */
public class PauseDialogContent extends JPanel implements ThemeListener {
  private static final int MARGIN = 20;

  private CustomizedButton btnSave = new CustomizedButton("save");
  private CustomizedButton btnRestart = new CustomizedButton("restart");
  private CustomizedButton btnRules = new CustomizedButton("rules");
  private CustomizedButton btnMainMenu = new CustomizedButton("main-menu");
  private ImageThemed bgImage = new ImageThemed("squared-background.png");

  /**
   * Constructs a PauseDialogContent object.
   */
  public PauseDialogContent() {
    ThemeUtils.addThemeListener(this);

    setLayout(new GridLayout(0, 1, 0, 10));
    setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

    // Add action listeners to the buttons
    btnMainMenu.addActionListener(e -> {
      // Navigate to the main menu
      EventsHandler.navigate("MainMenu");
      EventsHandler.hideDialog();
    });

    btnRestart.addActionListener(e -> {
      // Restart the game
      int typeP1 = EventsHandler.getController().getPlayer1Type();
      int typeP2 = EventsHandler.getController().getPlayer2Type();
      String nameP1 = EventsHandler.getController().getPlayer1Name();
      String nameP2 = EventsHandler.getController().getPlayer2Name();
      EventsHandler.getController().createModel(typeP1, typeP2, nameP1, nameP2);
      GameStatusHandler.startGame();
      EventsHandler.hideDialog();
    });

    btnSave.addActionListener(e -> {
      // EventsHandler.navigate("SavePage");
      EventsHandler.hideDialog();
      EventsHandler.showDialog(new SavePage(MainFrame.getLoadPage()), true);
    });

    btnRules.addActionListener(e -> {
      EventsHandler.hideDialog();
      EventsHandler.showDialog(new RulesPage(false), true);
    });

    add(btnSave);
    add(btnRestart);
    add(btnRules);
    add(btnMainMenu);
  }

  /**
   * Paints the background image of the dialog content.
   */
  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
  }

  /**
   * Updates the theme of the dialog content.
   */
  @Override
  public void updatedTheme() {
    repaint();
  }
}
