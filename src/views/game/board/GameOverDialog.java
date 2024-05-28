package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class GameOverDialog extends JPanel {
  private String winnerName;
  private CustomizedButton btnBack = new CustomizedButton("back-to-game");
  private CustomizedButton btnMenu = new CustomizedButton("main-menu");
  private Image bgImage;

  public GameOverDialog(String winnerName) {
    this.winnerName = winnerName;

    setLayout(new BorderLayout());

    try {
      bgImage = ImageIO.read(new File("assets/images/squared-background.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    btnMenu.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EventsHandler.navigate("MainMenu");
        EventsHandler.hideDialog();
      }
    });

    btnBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EventsHandler.hideDialog();
      }
    });

    // Panel that contains winner label and image
    JPanel winnerPanel = new JPanel();
    winnerPanel.setOpaque(false);
    winnerPanel.setLayout(new GridLayout(2, 1));
    winnerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    // Load and add crown image
    ImageIcon scaledCrownImage = ImageUtils.loadImage("crown.png", 100, 100);
    winnerPanel.add(scaledCrownImage != null ? new JLabel(scaledCrownImage) : null);
    winnerPanel.add(new JLabel(winnerName));
    add(winnerPanel, BorderLayout.CENTER);

    // Panel that contains buttons
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setOpaque(false);
    buttonsPanel.setLayout(new GridLayout(1, 2, 20, 0));
    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
    buttonsPanel.setPreferredSize(new Dimension(getWidth(), 75));
    buttonsPanel.add(btnBack);
    buttonsPanel.add(btnMenu);
    add(buttonsPanel, BorderLayout.SOUTH);

  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}
