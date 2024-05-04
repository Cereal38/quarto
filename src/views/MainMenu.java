package src.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JPanel {
  private JButton btnPlayerVsPlayer;
  private JButton btnPlayerVsAI;
  private JButton btnLoad;

  public MainMenu(ActionListener actionListener) {
    setLayout(new BorderLayout());

    JPanel navbar = new TopBarMainMenu(actionListener);
    add(navbar, BorderLayout.NORTH);

    // Button of the menu
    btnPlayerVsPlayer = new JButton("Player vs Player");
    btnPlayerVsAI = new JButton("Player vs AI");
    btnLoad = new JButton("Load");

    // Menu centered on the screen
    // The menu is at the middle of a 3x3 grid
    JPanel content = new JPanel();
    JPanel menu = new JPanel();
    content.setLayout(new GridLayout(3, 3));
    menu.setLayout(new GridLayout(4, 1, 0, 5));

    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(menu);
    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(new JLabel(""));

    JLabel titleLabel = new JLabel("Quarto");
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    menu.add(titleLabel);
    menu.add(btnPlayerVsPlayer);
    menu.add(btnPlayerVsAI);
    menu.add(btnLoad);

    // Add the content of the menu to the main panel
    add(content, BorderLayout.CENTER);

    // Ajout des action listeners
    btnPlayerVsPlayer.addActionListener(actionListener);
    btnPlayerVsAI.addActionListener(actionListener);
    btnLoad.addActionListener(actionListener);

  }

}
