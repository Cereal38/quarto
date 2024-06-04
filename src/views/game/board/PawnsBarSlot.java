package src.views.game.board;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import src.views.components.ImageThemed;
import src.views.components.Pawn;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents a slot in the pawns bar.
 */

public class PawnsBarSlot extends JPanel implements ThemeListener {

  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  private ImageThemed bgImage;
  private ImageThemed bgImageHovered;
  private ImageThemed bgImageSelected;
  private ImageThemed bgImageHinted;
  private Pawn pawn;

  /**
   * Constructor.
   *
   * @param pawn     The pawn associated with this slot.
   * @param position The position of the slot (PawnsBarSlot.LEFT or
   *                 PawnsBarSlot.RIGHT).
   */
  public PawnsBarSlot(Pawn pawn, int position) {

    ThemeUtils.addThemeListener(this);

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
      if (position == LEFT) {
        bgImage = new ImageThemed("pawns-bar-left-slot.png");
        bgImageHovered = new ImageThemed("pawns-bar-left-slot-hovered.png");
        bgImageSelected = new ImageThemed("pawns-bar-left-slot-selected.png");
        bgImageHinted = new ImageThemed("pawns-bar-left-slot-hint.png");
      } else {
        bgImage = new ImageThemed("pawns-bar-right-slot.png");
        bgImageHovered = new ImageThemed("pawns-bar-right-slot-hovered.png");
        bgImageSelected = new ImageThemed("pawns-bar-right-slot-selected.png");
        bgImageHinted = new ImageThemed("pawns-bar-right-slot-hint.png");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Set cursor pointer if selection phase, not paused and not empty slot
    if (EventsHandler.getController().isSelectionPhase() && !GameStatusHandler.isPaused() && pawn != null) {
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
      g.drawImage(bgImageSelected.getImage(), 0, 0, getWidth(), getHeight(), this);
      // If the pawn is hovered and the game is in the selection phase, display the
      // hovered image
    } else if ((pawn != null && pawn.isHovered()) && EventsHandler.getController().isSelectionPhase()
        && !GameStatusHandler.isPaused()) {
      g.drawImage(bgImageHovered.getImage(), 0, 0, getWidth(), getHeight(), this);
    } else if (pawn != null && pawn.isHint() && GameStatusHandler.isHintClicked()) {
      g.drawImage(bgImageHinted.getImage(), 0, 0, getWidth(), getHeight(), this);
    } else {
      g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
  }

  @Override
  public void updatedTheme() {
    repaint();
  }
}