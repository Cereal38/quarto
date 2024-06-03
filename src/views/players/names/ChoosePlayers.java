package src.views.players.names;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.views.components.GoBackButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedLabel;
import src.views.listeners.ThemeListener;
import src.views.utils.ThemeUtils;

public class ChoosePlayers extends JPanel implements ThemeListener {
  private ImageThemed bgImage = new ImageThemed("bg-board.png");
  private ImageThemed topbarImage = new ImageThemed("flat.png");

  public ChoosePlayers() {
    ThemeUtils.addThemeListener(this);

    setLayout(new BorderLayout());

    // Create and add the "Go Back" button with an image
    GoBackButton backButton = new GoBackButton();
    // Create top panel and add the "Go Back" button to it
    JPanel topPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(topbarImage.getImage(), 0, 0, getWidth(), getHeight(), this);
      }
    };
    topPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    topPanel.add(backButton, BorderLayout.WEST);

    // Add top panel to the north of ChoosePlayers
    add(topPanel, BorderLayout.NORTH);

    // Create the title label and add it to the center of the top panel
    TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(topbarImage.getImage(), 0, 0, getWidth(), getHeight(), this);
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

  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }
}
