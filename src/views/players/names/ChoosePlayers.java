package src.views.players.names;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    setLayout(new BorderLayout());

    // Compute dimensions
    int widthFrame = DimensionUtils.getMainFrameWidth();
    int heightFrame = DimensionUtils.getMainFrameHeight();
    int heightPawnsBar = heightFrame - HEIGHT_TOP_BAR;
    int widthPawnsBar = (int) (heightPawnsBar / 3.81); // We want to keep the ratio 3.81 (height/width)
    int pawnsBarPawnSize = Math.min((widthPawnsBar / 2) - 30, (heightPawnsBar / 8) - 30);
    int heightBoardWrapper = heightFrame - HEIGHT_TOP_BAR;
    int boardCellSize = (int) (heightBoardWrapper - heightBoardWrapper * 0.5) / 4;
    int horizontalMarginFields = widthFrame / 4;
    int verticalMarginFields = heightFrame / 4;

    // Register useful dimensions
    DimensionUtils.setBarCellSize(pawnsBarPawnSize);
    DimensionUtils.setBoardCellSize(boardCellSize);
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);

    // Setup title bar
    JPanel titleBar = new JPanel();
    JLabel titleLabel = new JLabel("Choose players");
    titleBar.add(titleLabel);

    // Setup fields
    JPanel fieldsWrapper = new JPanel();
    fieldsWrapper.setLayout(new GridBagLayout());
    fieldsWrapper.add(new PlayerFields());

    // Add components
    add(titleBar, BorderLayout.NORTH);
    add(fieldsWrapper, BorderLayout.CENTER);

    revalidate();
    repaint();
  }
}
