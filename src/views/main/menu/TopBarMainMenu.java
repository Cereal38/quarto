package src.views.main.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.ExitButton;
import src.views.components.LanguageButton;
import src.views.components.ManualButton;
import src.views.components.MusicButton;
import src.views.components.ThemeButton;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class TopBarMainMenu extends JPanel {

  private JButton musicButton, langButton, modeButton, exitButton, bookButton;
  private Image woodTexture;

  public TopBarMainMenu() {
    woodTexture = ImageUtils.loadImage("wood.jpeg", 50, 50).getImage();

    // Create buttons
    musicButton = new MusicButton();
    modeButton = new ThemeButton();
    langButton = new LanguageButton();
    exitButton = new ExitButton();
    bookButton = new ManualButton();

    // Set listeners to the buttons
    EventsHandler.setMusicButton((MusicButton) musicButton);
    EventsHandler.setLanguageButton((LanguageButton) langButton);
    EventsHandler.setManualButton((ManualButton) bookButton);
    EventsHandler.setExitButton((ExitButton) exitButton);

    // Buttons aligned on the left
    JPanel leftPanel = new LeftPanel();
    leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
    leftPanel.add(musicButton);
    leftPanel.add(modeButton);
    leftPanel.add(langButton);

    // Buttons aligned on the right
    JPanel rightPanel = new RightPanel();
    rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 10));
    rightPanel.add(bookButton);
    rightPanel.add(exitButton);

    setLayout(new BorderLayout());

    add(leftPanel, BorderLayout.WEST);
    add(rightPanel, BorderLayout.EAST);

    // Transparent background
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(woodTexture, 0, 0, this.getWidth(), this.getHeight(), this);
  }
}
