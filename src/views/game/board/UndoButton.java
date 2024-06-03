package src.views.game.board;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;
import src.views.utils.ThemeUtils;

/**
 * Represents a button for undoing the last action.
 */
public class UndoButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("undo.png");
  ImageIcon icon;

  /**
   * Constructs an UndoButton.
   */
  public UndoButton() {
    ThemeUtils.addThemeListener(this);

    image.setSize(32, 32);

    // Change style if can or can't undo
    if (EventsHandler.getController().canUndo()) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
      icon = new ImageIcon(image.getImage());
    } else {
      icon = new ImageIcon(ImageUtils.darkenImage(image.getImage()));
    }

    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.undo();
    });

    tooltip = new TranslatedString("undoButtonTooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
