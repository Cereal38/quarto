package src.views.game.board;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import src.views.utils.EventsHandler;

public class RightPanelTopBar extends JPanel {

  private Image bgImage;

  public RightPanelTopBar() {
    setLayout(new FlowLayout(FlowLayout.LEFT, 12, 14));

    PauseMenuButton btnPause = new PauseMenuButton();

    EventsHandler.setPauseMenuButton(btnPause);

    // Add action listeners to the buttons
    btnPause.addActionListener(e -> {
      EventsHandler.showDialog(new PauseDialogContent(), true);
    });

    add(btnPause);
    JButton historyButton = new JButton("History");
    historyButton.addActionListener(e -> {
      // Show the history panel
      EventsHandler.showDialog(new MovesHistoryDialog(), true);
    });
    add(historyButton);

    // Load background image
    try {
      bgImage = ImageIO.read(new File("assets/images/gameboard-right-top-bar.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }

}
