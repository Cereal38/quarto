package src.views.game.board;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.TranslatedString;
import src.views.utils.ImageUtils;

public class HistoryButton extends JButton {
  private TranslatedString tooltip;
  private boolean isLightTheme = true;
  // Load icon
  ImageIcon HistoryImg = ImageUtils.loadImage("history.png", 32, 32);
  ImageIcon HistoryWhiteImg = ImageUtils.loadImage("history.png", 32, 32);

  public HistoryButton() {

    // cursor : pointer
    setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add style
    setIcon(HistoryImg);
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);

    addActionListener(e -> {
    });

    tooltip = new TranslatedString("historyButton", this, true);
  }

  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    setIcon(isLightTheme ? HistoryImg : HistoryWhiteImg);
  }
}
