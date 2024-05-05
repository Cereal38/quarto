package src.views.MainMenu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.ExitButton;
import src.views.components.LanguageButton;
import src.views.components.ManualButton;
import src.views.components.MusicButton;
import src.views.components.ThemeButton;

public class TopBarMainMenu extends JPanel {

  private JButton musicButton, langButton, modeButton, exitButton, bookButton;

  public TopBarMainMenu() {

    // Create buttons
    musicButton = new MusicButton();
    modeButton = new ThemeButton();
    langButton = new LanguageButton();
    exitButton = new ExitButton();
    bookButton = new ManualButton();

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