package src.views.players.names;

import src.views.components.BorderCenterPanel;
import src.views.components.GoBackButton;
import src.views.components.TranslatedLabel;
import src.views.utils.EventsHandler;
import src.views.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class ChoosePlayers extends JPanel {
  private Image bgImage;
  private Image woodTexture;

  public ChoosePlayers() {
    woodTexture = Objects.requireNonNull(ImageUtils.loadImage("wood.jpeg", 50, 50)).getImage();

    setLayout(new BorderLayout());

    // Create and add the "Go Back" button with an image
    GoBackButton backButton = new GoBackButton();
    // Create top panel and add the "Go Back" button to it
    JPanel topPanel = new JPanel(new BorderLayout()){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(woodTexture, 0, 0, getWidth(), getHeight(), this);
      }
    };
    topPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    topPanel.add(backButton, BorderLayout.WEST);

    // Add top panel to the north of ChoosePlayers
    add(topPanel, BorderLayout.NORTH);

    // Create the title label and add it to the center of the top panel
    TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(woodTexture, 0, 0, getWidth(), getHeight(), this);
      }
    };
    labelPanel.add(titleLabel);
    topPanel.add(labelPanel, BorderLayout.CENTER);

    // Setup fields
    JPanel fieldsWrapper = new JPanel();
    fieldsWrapper.setOpaque(false);
    fieldsWrapper.setLayout(new GridBagLayout());
    fieldsWrapper.add(new PlayerFields());

    // Add components
    add(fieldsWrapper, BorderLayout.CENTER);

    try {
      bgImage = ImageIO.read(new File("assets/images/bg-board.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}
