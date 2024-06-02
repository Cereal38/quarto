package src.views.game.history;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import src.model.QuartoHistory;
import src.views.components.TranslatedString;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;

public class MovesHistory extends JScrollPane {

  private JPanel movesContainer;
  private int maxDisplayedMoves = 10; // Maximum displayed moves
  private Image bgImage;
  int isSelectedCounter = 0;
  private TranslatedString placedItAtStr = new TranslatedString("placed-it-at");
  private TranslatedString selectedThePawnStr = new TranslatedString("selected-the-pawn");

  public MovesHistory() {
    // Load the background image
    try {
        bgImage = ImageIO.read(new File("assets/images/pawns-bar.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setPreferredSize(new Dimension(00, DimensionUtils.getMainFrameHeight()));

    movesContainer = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
          }
        }
      };

    movesContainer.setLayout(new GridBagLayout());
    setViewportView(movesContainer);
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      clear();
    updateHistory();
  }

  public void addMove(String move, ImageIcon icon) {
    GameStatusHandler.addMove(move, icon);
    updateMovesContainer();
  }

  public void clear() {
    GameStatusHandler.clearMoves();
    updateMovesContainer();
  }

  private void updateMovesContainer() {
    movesContainer.removeAll();

    GridBagConstraints moveConstraints = new GridBagConstraints();
    moveConstraints.gridx = 0;
    moveConstraints.fill = GridBagConstraints.HORIZONTAL;
    moveConstraints.weightx = 1.0;

    int moveNumber = 1; // Start with 1 for the most recent move

    // Add moves to container with separators in between
    for (int i = GameStatusHandler.getMoveComponents().size() - 1; i >= 0; i--) {
      moveConstraints.gridy = 2 * i; // Move
      // Add move number label
      JLabel moveNumberLabel = new JLabel(moveNumber + " .");
      movesContainer.add(moveNumberLabel, moveConstraints);
      moveConstraints.gridx = 1;
      movesContainer.add(GameStatusHandler.getMoveComponents().get(i), moveConstraints);
      moveConstraints.gridx = 0;
        // Add separator that spans across both columns
        moveConstraints.gridy = 2 * (GameStatusHandler.getMoveComponents().size() - 1 - i) + 1; // Separator
        moveConstraints.gridwidth = 2; // Span across two columns
        movesContainer.add(new JSeparator(JSeparator.HORIZONTAL), moveConstraints);
        moveConstraints.gridwidth = 1; // Reset grid width
        // set color of the separator to black
        movesContainer.getComponent(movesContainer.getComponentCount() - 1).setForeground(java.awt.Color.BLACK);

      moveNumber++; // Increment the move number for the next iteration
    }

    movesContainer.revalidate();
    movesContainer.repaint();
    // Scroll to the top
    getVerticalScrollBar().setValue(0);
  }

  public void updateHistory() {
    if (EventsHandler.getController().getModel() == null) {
      return;
    }
    QuartoHistory save = EventsHandler.getController().getModel().getCurrentState();

    while (save != null) {
      String name = save.getName();
      int pawn = save.getIndexPawn();
      int x = save.getLine();
      int y = save.getColumn();
      String moveDescription;
      ImageIcon pawnIcon = null;
      if (name != null) {
        // y 0 is a, y 1 is b, etc.
        char column = (char) (y + 97);
        if (save.getState() == 0) {
            String pawnIconString = pawnNumberToString(pawn);
            pawnIcon = ImageUtils.loadImage(pawnIconString + ".png", 40, 40);
            moveDescription = "<html><font color='white'>" + name + "</font> " + selectedThePawnStr + " ";
        } else {
            moveDescription = "<html><font color='white'>" + name + "</font> " + placedItAtStr + " " + x + " - " + column;
        }
        // Add the move to the history at the top
        addMove(moveDescription, pawnIcon);
      }

      save = save.getPrevious();
    }
  }
    // to load the pawn images we need to turn the pawn number into a string ( 0 -> "0000.png" , 1 -> "0001.png" , "2" -> "0010.png" to "15" -> "1111.png")
    private String pawnNumberToString(int pawn) {
        String binary = Integer.toBinaryString(pawn);
        while (binary.length() < 4) {
          binary = "0" + binary;
        }
        return binary;
      }
}
