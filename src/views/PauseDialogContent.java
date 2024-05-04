package src.views;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.EventsHandler;
import src.views.components.TranslatedString;

public class PauseDialogContent extends JPanel {
  private JButton btnAbandon = new JButton();
  private JButton btnRestart = new JButton();
  private JButton btnSave = new JButton();
  private JButton btnRules = new JButton();
  private JButton btnMainMenu = new JButton();

  public PauseDialogContent() {
    setLayout(new GridLayout(0, 1));

    // Abandon button
    TranslatedString translatedBtnAbandon = new TranslatedString("abandon", btnAbandon);
    btnAbandon.setText(translatedBtnAbandon.getText());

    // Restart button
    TranslatedString translatedBtnRestart = new TranslatedString("restart", btnRestart);
    btnRestart.setText(translatedBtnRestart.getText());

    // Save button
    TranslatedString translatedBtnSave = new TranslatedString("save", btnSave);
    btnSave.setText(translatedBtnSave.getText());

    // Rules button
    TranslatedString translatedBtnRules = new TranslatedString("rules", btnRules);
    btnRules.setText(translatedBtnRules.getText());

    // Main menu button
    TranslatedString translatedBtnMainMenu = new TranslatedString("main-menu", btnMainMenu);
    btnMainMenu.setText(translatedBtnMainMenu.getText());
    btnMainMenu.addActionListener(e -> {
      // Navigate to the main menu
      EventsHandler.navigate("MainMenu");
      EventsHandler.hideDialog();
    });

    add(btnAbandon);
    add(btnRestart);
    add(btnSave);
    add(btnRules);
    add(btnMainMenu);
  }
}