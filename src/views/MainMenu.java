package src.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.EventsHandler;
import src.views.components.GridCenterPanel;
import src.views.components.TranslatedString;

public class MainMenu extends JPanel {
  private JButton btnPvP = new JButton();
  private JButton btnPvC = new JButton();
  private JButton btnLoad = new JButton();

  public MainMenu(ActionListener actionListener) {
    setLayout(new BorderLayout());

    JPanel navbar = new TopBarMainMenu(actionListener);
    add(navbar, BorderLayout.NORTH);

    // Player vs Player button
    TranslatedString translatedBtnPlayer = new TranslatedString("pvp", btnPvP);
    btnPvP.setText(translatedBtnPlayer.getText());
    btnPvP.addActionListener(e -> {
      EventsHandler.navigate("GameBoard");
    });

    // Player vs Computer button
    TranslatedString translatedBtnAi = new TranslatedString("pvc", btnPvC);
    btnPvC.setText(translatedBtnAi.getText());
    btnPvC.addActionListener(e -> {
      EventsHandler.navigate("GameBoard");
    });

    // Load button
    TranslatedString translatedBtnLoad = new TranslatedString("load", btnLoad);
    btnLoad.setText(translatedBtnLoad.getText());

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
    menu.add(btnPvP);
    menu.add(btnPvC);
    menu.add(btnLoad);

  }

}
