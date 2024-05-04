package src.views;

import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenu extends JPanel {
  private JButton btnAbandon;
  private JButton btnRestart;
  private JButton btnSave;
  private JButton btnRules;
  private JButton btnMainMenu;

  public PauseMenu(ActionListener actionListener) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    btnAbandon = new JButton("Abandon");
    btnRestart = new JButton("Restart");
    btnSave = new JButton("Save");
    btnRules = new JButton("Rules");
    btnMainMenu = new JButton("Main Menu");

    add(btnAbandon);
    add(btnRestart);
    add(btnSave);
    add(btnRules);
    add(btnMainMenu);

    btnAbandon.addActionListener(actionListener);
    btnRestart.addActionListener(actionListener);
    btnSave.addActionListener(actionListener);
    btnRules.addActionListener(actionListener);
    btnMainMenu.addActionListener(actionListener);
  }
}