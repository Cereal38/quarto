package src.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.views.utils.ImageUtils;

public class MainMenu extends JPanel {
  private JButton btnPlayerVsPlayer;
  private JButton btnPlayerVsAI;
  private JButton btnLoad;
  private JButton musicButton, langButton, modeButton, exitButton, bookButton;

  public MainMenu(ActionListener actionListener) {
    setLayout(new BorderLayout());

    // Chargement des icônes
    ImageIcon musicOnImg = ImageUtils.loadImage("music-on.png", 30, 30);
    ImageIcon musicOffImg = ImageUtils.loadImage("music-off.png", 30, 30);
    ImageIcon darkImg = ImageUtils.loadImage("dark.png", 30, 30);
    ImageIcon lightImg = ImageUtils.loadImage("light.png", 30, 30);
    ImageIcon exitImg = ImageUtils.loadImage("exit.png", 30, 30);
    ImageIcon frImg = ImageUtils.loadImage("fr.png", 30, 30);
    ImageIcon enImg = ImageUtils.loadImage("en.png", 30, 30);
    ImageIcon bookImg = ImageUtils.loadImage("book.png", 30, 30);

    // Création des boutons
    musicButton = ImageUtils.createButtonFromImage(musicOnImg);
    modeButton = ImageUtils.createButtonFromImage(darkImg);
    langButton = ImageUtils.createButtonFromImage(enImg);
    exitButton = ImageUtils.createButtonFromImage(exitImg);
    bookButton = ImageUtils.createButtonFromImage(bookImg);

    // Définir des action commands pour chaque bouton
    musicButton.setActionCommand("Music");
    modeButton.setActionCommand("DarkMode"); // Correspond à ce que "actionPerformed" attend
    langButton.setActionCommand("Language");
    exitButton.setActionCommand("Quit");
    bookButton.setActionCommand("Manual");

    // Navbar avec des boutons alignés différemment
    JPanel navbar = new JPanel(new BorderLayout());

    // Panneau pour les boutons alignés à gauche
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftPanel.add(musicButton);
    leftPanel.add(modeButton);
    leftPanel.add(langButton);

    // Panneau pour les boutons alignés à droite
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightPanel.add(bookButton);
    rightPanel.add(exitButton);

    // Ajout des sous-panneaux à la navbar
    navbar.add(leftPanel, BorderLayout.WEST);
    navbar.add(rightPanel, BorderLayout.EAST);

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
    exitButton.addActionListener(actionListener);
    musicButton.addActionListener(actionListener);
    langButton.addActionListener(actionListener);
    modeButton.addActionListener(actionListener); // Ajout de l'ActionListener
    bookButton.addActionListener(actionListener);
  }

  public void addNavbarActionListener(ActionListener actionListener) {
    musicButton.addActionListener(actionListener);
    modeButton.addActionListener(actionListener);
    langButton.addActionListener(actionListener);
    bookButton.addActionListener(actionListener);
    exitButton.addActionListener(actionListener);
  }
}
