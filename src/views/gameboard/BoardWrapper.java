package src.views.gameboard;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    // South
    JPanel southPanel = new JPanel();
    JButton southMargin = new JButton("");
    southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, (int) verticalMargin, 0));
    southPanel.add(southMargin);
    add(southPanel, BorderLayout.SOUTH);

    // North
    JPanel northPanel = new JPanel();
    JLabel northMargin = new JLabel("");
    northMargin.setBorder(BorderFactory.createEmptyBorder((int) verticalMargin, 0, 0, 0));
    northPanel.add(northMargin);
    add(northPanel, BorderLayout.NORTH);

    // West
    JPanel westPanel = new JPanel();
    JLabel westMargin = new JLabel("");
    westMargin.setBorder(BorderFactory.createEmptyBorder(0, (int) horizontalMargin, 0, 0));
    westPanel.add(westMargin);
    add(westPanel, BorderLayout.WEST);

    // East
    JPanel eastPanel = new JPanel();
    JLabel eastMargin = new JLabel("");
    eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, (int) horizontalMargin));
    eastPanel.add(eastMargin);
    add(eastPanel, BorderLayout.EAST);

    // Board
    add(new Board((int) cellSize, (int) gap), BorderLayout.CENTER);

  }
}
