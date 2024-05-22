package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.listeners.GameStatusListener;
import src.views.utils.DimensionUtils;
import src.views.utils.GameStatusHandler;

public class GameBoard extends JPanel implements GameStatusListener {

  private static final int HEIGHT_TOP_BAR = 60;

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
    int heightPawnsBar = heightFrame - HEIGHT_TOP_BAR;
    int widthPawnsBar = (int) (heightPawnsBar / 3.81); // We want to keep the ratio 3.81 (height/width)
    int pawnsBarPawnSize = Math.min((widthPawnsBar / 2) - 30, (heightPawnsBar / 8) - 30);
    int widthBoardWrapper = widthFrame - widthPawnsBar;
    int heightBoardWrapper = heightFrame - HEIGHT_TOP_BAR;
    int boardCellSize = (int) (heightBoardWrapper) / 4;
    int boardGap = (heightBoardWrapper - boardCellSize * 4) / 5;
    int horizontalMarginBoard = (int) (widthBoardWrapper / 2 - boardCellSize * 2 - boardGap * 1.5);
    int verticalMarginBoard = boardGap;

    // Register useful dimensions
    DimensionUtils.setBarCellSize(pawnsBarPawnSize);
    DimensionUtils.setBoardCellSize(boardCellSize);
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);

    // Setup top bar
    TopBarGameBoard topBarGameBoard = new TopBarGameBoard(widthTopBar, HEIGHT_TOP_BAR);

    // Setup pawns bar
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);
    PawnsBar pawnsBar = new PawnsBar(widthPawnsBar, heightPawnsBar);

    // Setup board. Add a wrapper to center the board and add margins
    BorderCenterPanel boardWrapper = new BorderCenterPanel(
        new Board(widthBoardWrapper, heightBoardWrapper, boardCellSize), verticalMarginBoard, horizontalMarginBoard,
        verticalMarginBoard, horizontalMarginBoard);

    // Add components
    add(topBarGameBoard, BorderLayout.NORTH);
    add(boardWrapper, BorderLayout.CENTER);
    add(pawnsBar, BorderLayout.EAST);

    revalidate();
    repaint();
  }
}
