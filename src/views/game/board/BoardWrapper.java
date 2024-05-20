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
    int hFrame = DimensionUtils.getMainFrameHeight(); // DONE
    int wFrame = DimensionUtils.getMainFrameWidth(); // DONE
    int hTopBar = DimensionUtils.getBoardTopBarHeight(); // DONE
    int hPawnsBar = DimensionUtils.getBoardPawnsBarHeight(); // DONE
    int height = hFrame - hTopBar - hPawnsBar; // DONE
    int width = wFrame; // DONE

    // Compute the cell size and the gap between cells
    float cellSize = (height - height * GAP_FACTOR) / 4; // DONE
    float gap = (height - cellSize * 4) / 5; // DONE

    DimensionUtils.setBoardCellSize((int) cellSize);

    // Get the position of the top left corner of the board
    float horizontalMargin = (float) (width / 2 - cellSize * 2 - gap * 1.5); // DONE
    float verticalMargin = gap; // DONE

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
        int wHistory = DimensionUtils.getHistoryWidth();
        int height = hFrame - hTopBar - hPawnsBar;
        int width = wFrame - wHistory;

        float cellSize = (height - height * GAP_FACTOR) / 4;
        float gap = (height - cellSize * 4) / 5;

        DimensionUtils.setBoardCellSize((int) cellSize);

        float horizontalMargin = (float) (width / 2 - cellSize * 2 - gap * 1.5);
        float verticalMargin = gap;

        centerPanel.setMargins((int) verticalMargin, (int) horizontalMargin, (int) verticalMargin,
            (int) horizontalMargin);
      }
    });

  }
}
