package src.views.game.board;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import src.views.utils.EventsHandler;

public class LeftPanelTopBar extends JPanel {

  private Image bgImage;

  public LeftPanelTopBar() {
    setLayout(new FlowLayout(FlowLayout.LEFT, 12, 17));

    UndoButton btnUndo = new UndoButton();
    RedoButton btnRedo = new RedoButton();

    EventsHandler.setUndoButton(btnUndo);
    EventsHandler.setRedoButton(btnRedo);

    add(btnUndo);
    add(btnRedo);

    // Load background image
    try {
      bgImage = ImageIO.read(new File("assets/images/gameboard-action-buttons-container.png"));
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
