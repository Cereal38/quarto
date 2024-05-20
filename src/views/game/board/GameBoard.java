package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.game.history.MovesHistory;
import src.views.utils.DimensionUtils;

public class GameBoard extends JPanel {

  private static final int WIDTH_HISTORY = 250;

  public GameBoard() {
    setLayout(new BorderLayout());

    int heightFrame = DimensionUtils.getMainFrameHeight();
    int widthFrame = DimensionUtils.getMainFrameWidth();

    // Setup top bar
    TopBarGameBoard topBarGameBoard = new TopBarGameBoard();
    int widthTopBar = widthFrame;
    int heightTopBar = 50;
    topBarGameBoard.setPreferredSize(new Dimension(widthTopBar, heightTopBar));
    DimensionUtils.setBoardTopBar(widthTopBar, heightTopBar);

    // Setup pawns bar. Add a wrapper to add right margin
    PawnsBar pawnsBar = new PawnsBar();
    int widthPawnsBar = widthFrame - WIDTH_HISTORY;
    int heightPawnsBar = 70;
    pawnsBar.setPreferredSize(new Dimension(widthPawnsBar, heightPawnsBar));
    DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);
    BorderCenterPanel pawnsBarWrapper = new BorderCenterPanel(pawnsBar, 0, 0, 0, WIDTH_HISTORY);

    BoardWrapper boardWrapper = new BoardWrapper();

    MovesHistory movesHistory = new MovesHistory();
    BorderCenterPanel movesHistoryWrapper = new BorderCenterPanel(movesHistory, 50, 0, 50, 0);
    movesHistoryWrapper.setPreferredSize(new Dimension(WIDTH_HISTORY, heightFrame - heightTopBar - heightPawnsBar));
    DimensionUtils.setHistory(WIDTH_HISTORY, heightFrame - heightTopBar - heightPawnsBar);

    // Add components
    add(topBarGameBoard, BorderLayout.NORTH);
    add(boardWrapper, BorderLayout.CENTER);
    add(movesHistoryWrapper, BorderLayout.EAST);
    add(pawnsBarWrapper, BorderLayout.SOUTH);

  }
}
