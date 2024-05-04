package src.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.GridCenterPanel;
import src.views.components.TranslatedString;

public class MainMenu extends JPanel {
  private JButton btnPlayerVsPlayer;
  private JButton btnPlayerVsAi;
  private JButton btnLoad;

  public MainMenu(ActionListener actionListener) {
    setLayout(new BorderLayout());

    JPanel navbar = new TopBarMainMenu(actionListener);
    add(navbar, BorderLayout.NORTH);

    // Button of the menu
    btnPlayerVsPlayer = new JButton("Player vs Player");
    btnPlayerVsAi = new JButton("Player vs AI");
    btnLoad = new JButton();
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
    menu.add(btnPlayerVsPlayer);
    menu.add(btnPlayerVsAi);
    menu.add(btnLoad);

    // Ajout des action listeners
    btnPlayerVsPlayer.addActionListener(actionListener);
    btnPlayerVsAi.addActionListener(actionListener);
    // btnLoad.addActionListener(actionListener);

  }

}
