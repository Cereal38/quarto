package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.CustomizedButton;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

public class GameOverDialog extends JPanel {
  private CustomizedButton btnBack = new CustomizedButton("back-to-game");
  private CustomizedButton btnMenu = new CustomizedButton("main-menu");
  private Image bgImage;

  public GameOverDialog(String winnerName) {
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
    winnerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Use GridBagLayout for precise positioning and sizing
    GridBagLayout gridBagLayout = new GridBagLayout();
    winnerPanel.setLayout(gridBagLayout);
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    // Load and add crown image
    ImageIcon scaledCrownImage = ImageUtils.loadImage("crown.png", 100, 100);
    JLabel crownLabel = new JLabel(scaledCrownImage);
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = GridBagConstraints.CENTER;
    gridBagLayout.setConstraints(crownLabel, gridBagConstraints);
    winnerPanel.add(crownLabel);

    // Add 20-pixel vertical space
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = GridBagConstraints.VERTICAL;
    gridBagConstraints.ipadx = 0;
    gridBagConstraints.ipady = 20; // set vertical padding to 20 pixels
    gridBagLayout.setConstraints(Box.createVerticalStrut(20), gridBagConstraints);
    winnerPanel.add(Box.createVerticalStrut(20));

    // Create and add winner label with larger font
    JLabel winnerLabel = new JLabel(winnerName);
    Font font = winnerLabel.getFont().deriveFont(Font.BOLD, 24); // set font to bold and 24-point size
    winnerLabel.setFont(font);
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = GridBagConstraints.CENTER;
    gridBagLayout.setConstraints(winnerLabel, gridBagConstraints);
    winnerPanel.add(winnerLabel);

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
