package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.game.history.MovesHistory;
import src.views.listeners.GameStatusListener;
import src.views.utils.DimensionUtils;
import src.views.utils.GameStatusHandler;

public class GameBoard extends JPanel implements GameStatusListener {

  private static final int WIDTH_HISTORY = 250;
  private static final int HEIGHT_TOP_BAR = 60;
  private static final float BOARD_GAP_FACTOR = (float) 0.3;
  private static final int PAWNS_BAR_GAP = 10;

  public GameBoard() {

    // Register this class as a game status listener
    // update() will be called when informListeners() is called
    GameStatusHandler.addGameStatusListener(this);

    update();

    // Resize listener
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        update();
      }
    });

  }

  /**
   * All the purpose of this method is to refresh the whole game board. We compute
   * the dimensions of the components here and pass them as props to components.
   */
  @Override
  public void update() {

    // Clear the board
    removeAll();

    setLayout(new BorderLayout());

    // Compute dimensions
    int widthFrame = DimensionUtils.getMainFrameWidth();
    int heightFrame = DimensionUtils.getMainFrameHeight();
    int widthTopBar = widthFrame;
    int widthPawnsBar = widthFrame - WIDTH_HISTORY;
    int pawnsBarPawnSize = Math.min((widthPawnsBar / 8) - (PAWNS_BAR_GAP * 2), 80);
    int heightPawnsBar = pawnsBarPawnSize * 2 + PAWNS_BAR_GAP * 2;
    int widthBoardWrapper = widthFrame - WIDTH_HISTORY;
    int heightBoardWrapper = heightFrame - HEIGHT_TOP_BAR - heightPawnsBar;
    int boardCellSize = (int) (heightBoardWrapper - heightBoardWrapper * BOARD_GAP_FACTOR) / 4;
    int boardGap = (heightBoardWrapper - boardCellSize * 4) / 5;
    int horizontalMarginBoard = (int) (widthBoardWrapper / 2 - boardCellSize * 2 - boardGap * 1.5);
    int verticalMarginBoard = boardGap;

    // Register useful dimensions
    DimensionUtils.setBarCellSize(pawnsBarPawnSize);
    DimensionUtils.setBoardCellSize(boardCellSize);

    // Setup top bar
    TopBarGameBoard topBarGameBoard = new TopBarGameBoard(widthTopBar, HEIGHT_TOP_BAR);

    // Setup pawns bar. Add a wrapper to add right margin
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);
    PawnsBar pawnsBar = new PawnsBar(widthPawnsBar, heightPawnsBar, PAWNS_BAR_GAP);
    BorderCenterPanel pawnsBarWrapper = new BorderCenterPanel(pawnsBar, 0, 0, 0, WIDTH_HISTORY);

    // Setup board. Add a wrapper to center the board and add margins
    BorderCenterPanel boardWrapper = new BorderCenterPanel(
        new Board(widthBoardWrapper, heightBoardWrapper, boardCellSize), verticalMarginBoard, horizontalMarginBoard,
        verticalMarginBoard, horizontalMarginBoard);

    // Setup history
    MovesHistory movesHistory = new MovesHistory();
    BorderCenterPanel movesHistoryWrapper = new BorderCenterPanel(movesHistory, 50, 0, 50, 0);
    movesHistoryWrapper.setPreferredSize(new Dimension(WIDTH_HISTORY, heightFrame - HEIGHT_TOP_BAR - heightPawnsBar));
    DimensionUtils.setHistory(WIDTH_HISTORY, heightFrame - HEIGHT_TOP_BAR - heightPawnsBar);

    // Add components
    add(topBarGameBoard, BorderLayout.NORTH);
    add(boardWrapper, BorderLayout.CENTER);
    add(movesHistoryWrapper, BorderLayout.EAST);
    add(pawnsBarWrapper, BorderLayout.SOUTH);

    revalidate();
    repaint();
  }
}
