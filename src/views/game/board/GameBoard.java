package src.views.game.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import src.views.components.BorderCenterPanel;
import src.views.game.history.MovesHistory;
import src.views.utils.DimensionUtils;

public class GameBoard extends JPanel {

    public GameBoard() {
        setLayout(new BorderLayout());

        int widthFrame = DimensionUtils.getMainFrameWidth();

        // Setup top bar
        TopBarGameBoard topBarGameBoard = new TopBarGameBoard();
        int widthTopBar = widthFrame;
        int heightTopBar = 50;
        topBarGameBoard.setPreferredSize(new Dimension(widthTopBar, heightTopBar));
        DimensionUtils.setBoardTopBar(widthTopBar, heightTopBar);

        // Setup pawns bar
        PawnsBar pawnsBar = new PawnsBar();
        int widthPawnsBar = widthFrame;
        int heightPawnsBar = 130;
        pawnsBar.setPreferredSize(new Dimension(widthPawnsBar, heightPawnsBar));
        DimensionUtils.setBoardPawnsBar(widthPawnsBar, heightPawnsBar);

        BoardWrapper boardWrapper = new BoardWrapper();

        MovesHistory movesHistory = new MovesHistory();
        BorderCenterPanel movesHistoryWrapper = new BorderCenterPanel(movesHistory, 50, 0, 50, 0);

        // Add components
        add(topBarGameBoard, BorderLayout.NORTH);
        add(boardWrapper, BorderLayout.CENTER);
        add(movesHistoryWrapper, BorderLayout.EAST);
        add(pawnsBar, BorderLayout.SOUTH);

    }
}
