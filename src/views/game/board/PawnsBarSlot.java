package src.views.game.board;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import src.views.components.Pawn;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;

public class PawnsBarSlot extends JPanel {

  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  private Image bgImage;
  private Image bgImageHovered;
  private Image bgImageSelected;
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
    gbc.insets = new Insets(0, 6, 6, 0);

    // If the position is on the left, increase the offset to the right because
    // there is no right border on the image
    if (position == LEFT) {
      gbc.insets = new Insets(0, 18, 6, 0);
    }

    if (pawn != null) {
      add(pawn, gbc);
    }

    try {
      // TODO: Load bg only once
      if (position == LEFT) {
        bgImage = ImageIO.read(new File("assets/images/pawns-bar-left-slot.png"));
        bgImageHovered = ImageIO.read(new File("assets/images/pawns-bar-left-slot-hovered.png"));
        bgImageSelected = ImageIO.read(new File("assets/images/pawns-bar-left-slot-selected.png"));
      } else {
        bgImage = ImageIO.read(new File("assets/images/pawns-bar-right-slot.png"));
        bgImageHovered = ImageIO.read(new File("assets/images/pawns-bar-right-slot-hovered.png"));
        bgImageSelected = ImageIO.read(new File("assets/images/pawns-bar-right-slot-selected.png"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Set cursor pointer if selection phase and not paused
    if (EventsHandler.getController().isSelectionPhase() && !GameStatusHandler.isPaused()) {
      setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    // Repaint on mouse enter && mouse exit to trigger visual hover effect
    MouseListener mouseListener = new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        repaint();
      }

      @Override
      public void mouseExited(MouseEvent e) {
        repaint();
      }
    };

    addMouseListener(mouseListener);
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    if (pawn != null && pawn.isSelected()) {
      g.drawImage(bgImageSelected, 0, 0, getWidth(), getHeight(), this);
      // If the pawn is hovered and the game is in the selection phase, display the
      // hovered image
    } else if ((pawn != null && pawn.isHovered()) && EventsHandler.getController().isSelectionPhase()
        && !GameStatusHandler.isPaused()) {
      g.drawImage(bgImageHovered, 0, 0, getWidth(), getHeight(), this);
    } else {
      g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
  }
}