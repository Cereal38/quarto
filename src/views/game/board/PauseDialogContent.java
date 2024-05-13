package src.views.game.board;

import java.awt.GridLayout;
import javax.swing.JPanel;
import src.views.components.TranslatedButton;
import src.views.rules.RulesPage;
import src.views.utils.EventsHandler;

public class PauseDialogContent extends JPanel {
  private TranslatedButton btnAbandon = new TranslatedButton("abandon");
  private TranslatedButton btnRestart = new TranslatedButton("restart");
  private TranslatedButton btnSave = new TranslatedButton("save");
  private TranslatedButton btnRules = new TranslatedButton("rules");
  private TranslatedButton btnMainMenu = new TranslatedButton("main-menu");

  public PauseDialogContent() {
    setLayout(new GridLayout(0, 1));

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

    add(btnAbandon);
    add(btnRestart);
    add(btnSave);
    add(btnRules);
    add(btnMainMenu);
  }
}