package src.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import src.views.utils.ImageUtils;

public class TopBarMainMenu extends JPanel {

  private JButton musicButton, langButton, modeButton, exitButton, bookButton;

  public TopBarMainMenu(ActionListener actionListener) {
    // Load icons
    ImageIcon musicOnImg = ImageUtils.loadImage("music-on.png", 30, 30);
    ImageIcon musicOffImg = ImageUtils.loadImage("music-off.png", 30, 30);
    ImageIcon darkImg = ImageUtils.loadImage("dark.png", 30, 30);
    ImageIcon lightImg = ImageUtils.loadImage("light.png", 30, 30);
    ImageIcon exitImg = ImageUtils.loadImage("exit.png", 30, 30);
    ImageIcon frImg = ImageUtils.loadImage("fr.png", 30, 30);
    ImageIcon enImg = ImageUtils.loadImage("en.png", 30, 30);
    ImageIcon bookImg = ImageUtils.loadImage("book.png", 30, 30);

    // Create buttons
    musicButton = ImageUtils.createButtonFromImage(musicOnImg);
    modeButton = ImageUtils.createButtonFromImage(darkImg);
    langButton = ImageUtils.createButtonFromImage(enImg);
    exitButton = ImageUtils.createButtonFromImage(exitImg);
    bookButton = ImageUtils.createButtonFromImage(bookImg);

    // Add actions to buttons
    musicButton.setActionCommand("Music");
    modeButton.setActionCommand("DarkMode");
    langButton.setActionCommand("Language");
    exitButton.setActionCommand("Quit");
    bookButton.setActionCommand("Manual");
    exitButton.addActionListener(actionListener);
    musicButton.addActionListener(actionListener);
    langButton.addActionListener(actionListener);
    modeButton.addActionListener(actionListener);
    bookButton.addActionListener(actionListener);

    // Buttons aligned on the left
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
    leftPanel.add(musicButton);
    leftPanel.add(modeButton);
    leftPanel.add(langButton);

    // Buttons aligned on the right
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
    rightPanel.add(bookButton);
    rightPanel.add(exitButton);

    setLayout(new BorderLayout());

    add(leftPanel, BorderLayout.WEST);
    add(rightPanel, BorderLayout.EAST);

  }
}