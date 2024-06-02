package src.views.players.names;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.views.components.TranslatedLabel;

public class ChoosePlayers extends JPanel {

  private Image bgImage;

  public ChoosePlayers() {

    setLayout(new GridLayout(3, 1));

    // Setup title bar
    JPanel titleWrapper = new JPanel();
    titleWrapper.setOpaque(false);
    titleWrapper.setLayout(new BorderLayout());
    TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label horizontally
    titleWrapper.add(titleLabel, BorderLayout.SOUTH);

    // Setup fields
    JPanel fieldsWrapper = new JPanel();
    fieldsWrapper.setOpaque(false);
    fieldsWrapper.setLayout(new GridBagLayout());
    fieldsWrapper.add(new PlayerFields());

    // Add components
    add(titleWrapper);
    add(fieldsWrapper);

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
