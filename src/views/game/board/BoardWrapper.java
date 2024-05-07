package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.utils.DimensionUtils;

/**
 * All the meaning of this class is to center the board in the middle of the
 * screen.
 * 
 */
public class BoardWrapper extends JPanel {

  private static final float GAP_FACTOR = (float) 0.3;

  public BoardWrapper() {
    setLayout(new BorderLayout());

    // Compute the size of the panel
    int hFrame = DimensionUtils.getMainFrameHeight();
    int wFrame = DimensionUtils.getMainFrameWidth();
    int hTopBar = DimensionUtils.getBoardTopBarHeight();
    int hPawnsBar = DimensionUtils.getBoardPawnsBarHeight();
    int height = hFrame - hTopBar - hPawnsBar;
    int width = wFrame;

    // Compute the cell size and the gap between cells
    float cellSize = (height - height * GAP_FACTOR) / 4;
    float gap = (height - cellSize * 4) / 5;

    // Get the position of the top left corner of the board
    float horizontalMargin = (float) (width / 2 - cellSize * 2 - gap * 1.5);
    float verticalMargin = gap;

    BorderCenterPanel centerPanel = new BorderCenterPanel(new Board((int) cellSize), (int) verticalMargin,
        (int) horizontalMargin, (int) verticalMargin, (int) horizontalMargin);

    add(centerPanel, BorderLayout.CENTER);

    // Resize listener
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int hFrame = DimensionUtils.getMainFrameHeight();
        int wFrame = DimensionUtils.getMainFrameWidth();
        int hTopBar = DimensionUtils.getBoardTopBarHeight();
        int hPawnsBar = DimensionUtils.getBoardPawnsBarHeight();
        int height = hFrame - hTopBar - hPawnsBar;
        int width = wFrame;

        float cellSize = (height - height * GAP_FACTOR) / 4;
        float gap = (height - cellSize * 4) / 5;

        float horizontalMargin = (float) (width / 2 - cellSize * 2 - gap * 1.5);
        float verticalMargin = gap;

        centerPanel.setMargins((int) verticalMargin, (int) horizontalMargin, (int) verticalMargin,
            (int) horizontalMargin);
      }
    });

  }
}
