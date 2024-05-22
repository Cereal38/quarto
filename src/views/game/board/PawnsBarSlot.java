package src.views.game.board;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import src.views.components.Pawn;

public class PawnsBarSlot extends JPanel {

  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  private Image bgImage;
  private Pawn pawn;

  /**
   * Constructor.
   *
   * @param pawn
   * @param position: PawnsBarSlot.LEFT or PawnsBarSlot.RIGHT
   */
  public PawnsBarSlot(Pawn pawn, int position) {

    this.pawn = pawn;

    // Center the pawn but add a offset to the top (because of the border image)
    // Also add a offset to the right to improve visual
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 6, 12, 0);

    // If the position is on the left, increase the offset to the right because
    // there is no right border on the image
    if (position == LEFT) {
      gbc.insets = new Insets(0, 18, 12, 0);
    }

    add(pawn, gbc);

    try {
      // TODO: Load bg only once
      if (position == LEFT) {
        bgImage = ImageIO.read(new File("assets/images/pawns-bar-left-slot.png"));
      } else {
        bgImage = ImageIO.read(new File("assets/images/pawns-bar-right-slot.png"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension pawnSize = pawn.getPreferredSize();
    return new Dimension(pawnSize.width, pawnSize.height);
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }

}
