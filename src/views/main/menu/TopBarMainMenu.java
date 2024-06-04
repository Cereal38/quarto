package src.views.main.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.components.ExitButton;
import src.views.components.ImageThemed;
import src.views.components.LanguageButton;
import src.views.components.ManualButton;
import src.views.components.MusicButton;
import src.views.components.ThemeButton;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtils;

/**
 * The TopBarMainMenu class represents the top bar of the main menu,
 * containing buttons for music control, theme change, language change,
 * opening the manual, and exiting the application.
 *
 * <p>The class extends the JPanel class and implements the ThemeListener interface
 * to listen for theme change events.</p>
 */

public class TopBarMainMenu extends JPanel implements ThemeListener {

  private JButton langButton, modeButton, exitButton, bookButton,musicButton;
  private ImageThemed topbarImage = new ImageThemed("flat.png");


  /**
   * Constructs a TopBarMainMenu object.
   * Initializes the buttons for music control, theme change, language change,
   * opening the manual, and exiting the application.
   */
  public TopBarMainMenu() {
    ThemeUtils.addThemeListener(this);

    // Create buttons
    modeButton = new ThemeButton();
    langButton = new LanguageButton();
    exitButton = new ExitButton();
    bookButton = new ManualButton();
    musicButton = new MusicButton();


    // Buttons aligned on the left
    JPanel leftPanel = new JPanel();
    leftPanel.setOpaque(false);
    leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
    leftPanel.add(musicButton);
    leftPanel.add(modeButton);
    leftPanel.add(langButton);

    // Buttons aligned on the right
    JPanel rightPanel = new JPanel();
    rightPanel.setOpaque(false);
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
    g.drawImage(topbarImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }
}
