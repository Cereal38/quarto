package src.views.players.names;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import src.views.components.TranslatedLabel;
import src.views.utils.DimensionUtils;

public class ChoosePlayers extends JPanel {

  private static final int HEIGHT_TOP_BAR = 60;

  public ChoosePlayers() {

    update();

    // Resize listener
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        update();
      }
    });

  }

  public void update() {

    // Clear the board
    removeAll();

    setLayout(new GridLayout(3, 1));

    // Compute dimensions
    int heightFrame = DimensionUtils.getMainFrameHeight();
    int heightPawnsBar = heightFrame - HEIGHT_TOP_BAR;
    int widthPawnsBar = (int) (heightPawnsBar / 3.81); // We want to keep the ratio 3.81 (height/width)
    int pawnsBarPawnSize = Math.min((widthPawnsBar / 2) - 30, (heightPawnsBar / 8) - 30);
    int heightBoardWrapper = heightFrame - HEIGHT_TOP_BAR;
    int boardCellSize = (int) (heightBoardWrapper - heightBoardWrapper * 0.5) / 4;

    // Register useful dimensions
    DimensionUtils.setBarCellSize(pawnsBarPawnSize);
    DimensionUtils.setBoardCellSize(boardCellSize);
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);

    // Setup title bar
    JPanel titleWrapper = new JPanel();
    titleWrapper.setLayout(new GridBagLayout());
    TranslatedLabel titleLabel = new TranslatedLabel("enter-players-names");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleWrapper.add(titleLabel);

    // Setup fields
    JPanel fieldsWrapper = new JPanel();
    fieldsWrapper.setLayout(new GridBagLayout());
    fieldsWrapper.add(new PlayerFields());

    // Add components
    add(titleWrapper);
    add(fieldsWrapper);

    revalidate();
    repaint();
  }
}
