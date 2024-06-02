package src.views.game.board;

import java.awt.GridLayout;
import javax.swing.JPanel;
import src.views.components.Field;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.ThemeUtils;

public class CenterPanelTopBar extends JPanel implements ThemeListener {

  private ImageThemed bgImage = new ImageThemed("gameboard-center-top-bar.png");
  private Field player1Name;
  private Field player2Name;
  private Field selectionPhase;
  private Field playPhase;

  public CenterPanelTopBar() {
    ThemeUtils.addThemeListener(this);

    setLayout(new GridLayout(1, 4));

    String selectionPhaseStr = new TranslatedString("select-pawn").getText();
    String playPhaseStr = new TranslatedString("play-pawn").getText();
    player1Name = new Field(EventsHandler.getController().getPlayer1Name(),
        EventsHandler.getController().getCurrentPlayer() == 1);
    player2Name = new Field(EventsHandler.getController().getPlayer2Name(),
        EventsHandler.getController().getCurrentPlayer() == 2);
    selectionPhase = new Field(selectionPhaseStr, EventsHandler.getController().isSelectionPhase());
    playPhase = new Field(playPhaseStr, EventsHandler.getController().isPlayPhase());

    add(player1Name);
    add(player2Name);
    add(selectionPhase);
    add(playPhase);

  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    // Draw background image
    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
  }

  @Override
  public void updatedTheme() {
    repaint();
  }

}
