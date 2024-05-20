package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.game.history.MovesHistory;
import src.views.listeners.GameStatusListener;
import src.views.utils.DimensionUtils;
import src.views.utils.GameStatusHandler;

public class GameBoard extends JPanel implements GameStatusListener {

  private static final int WIDTH_HISTORY = 250;
  private static final int HEIGHT_TOP_BAR = 50;
  private static final int HEIGHT_PAWNS_BAR = 70;

  public GameBoard() {

    // Register this class as a game status listener
    // update() will be called when informListeners() is called
    GameStatusHandler.addGameStatusListener(this);

    update();

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
    int pawnsBarPawnSize = widthPawnsBar / 16;

    // Setup top bar
    TopBarGameBoard topBarGameBoard = new TopBarGameBoard(widthTopBar, HEIGHT_TOP_BAR);
    DimensionUtils.setBoardTopBar(widthTopBar, HEIGHT_TOP_BAR); // TODO: Delete this if not needed anymore

    // Setup pawns bar. Add a wrapper to add right margin
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, HEIGHT_PAWNS_BAR);
    PawnsBar pawnsBar = new PawnsBar(widthPawnsBar, HEIGHT_PAWNS_BAR);
    BorderCenterPanel pawnsBarWrapper = new BorderCenterPanel(pawnsBar, 0, 0, 0, WIDTH_HISTORY);
    DimensionUtils.setBarCellSize(pawnsBarPawnSize);

    // Setup board
    BoardWrapper boardWrapper = new BoardWrapper();

    // Setup history
    MovesHistory movesHistory = new MovesHistory();
    BorderCenterPanel movesHistoryWrapper = new BorderCenterPanel(movesHistory, 50, 0, 50, 0);
    movesHistoryWrapper.setPreferredSize(new Dimension(WIDTH_HISTORY, heightFrame - HEIGHT_TOP_BAR - HEIGHT_PAWNS_BAR));
    DimensionUtils.setHistory(WIDTH_HISTORY, heightFrame - HEIGHT_TOP_BAR - HEIGHT_PAWNS_BAR);

    // Add components
    add(topBarGameBoard, BorderLayout.NORTH);
    add(boardWrapper, BorderLayout.CENTER);
    add(movesHistoryWrapper, BorderLayout.EAST);
    add(pawnsBarWrapper, BorderLayout.SOUTH);

    revalidate();
    repaint();
  }
}
