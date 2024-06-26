package src.views.components;

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
 * Represents a button for redoing an action.
 */
public class RedoButton extends JButton implements ThemeListener {
  private TranslatedString tooltip;
  private ImageThemed image = new ImageThemed("redo.png");
  ImageIcon icon;

  /**
   * Constructs a RedoButton.
   */
  public RedoButton() {
    ThemeUtils.addThemeListener(this);

    image.setSize(32, 32);

    if (EventsHandler.getController().canRedo()) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
      icon = new ImageIcon(image.getImage());
    } else {
      icon = new ImageIcon(ImageUtils.darkenImage(image.getImage()));
    }

    setIcon(icon);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
      GameStatusHandler.redo();
    });

    tooltip = new TranslatedString("redoButtonTooltip", this, true);
  }

  @Override
  public void updatedTheme() {
    ImageIcon icon = new ImageIcon(image.getImage());
    setIcon(icon);
  }
}
