package src.views.game.history;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import src.model.QuartoHistory;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

public class MovesHistory extends JScrollPane implements ThemeListener {

  private JPanel movesContainer;
  private int maxDisplayedMoves = 10; // Maximum displayed moves
  int isSelectedCounter = 0;
  private TranslatedString placedItAtStr = new TranslatedString("placed-it-at");
  private TranslatedString selectedThePawnStr = new TranslatedString("selected-the-pawn");
  private ImageThemed bgImage = new ImageThemed("squared-background.png");

  public MovesHistory() {
    ThemeUtils.addThemeListener(this);

    setPreferredSize(new Dimension(00, DimensionUtils.getMainFrameHeight()));

    movesContainer = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
          g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
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
      moveConstraints.insets = new Insets(0, 70, 0, 70); // Set insets to reduce space

      movesContainer.add(GameStatusHandler.getMoveComponents().get(i), moveConstraints);

      // Add separator
      moveConstraints.gridy++;
      moveConstraints.gridwidth = 2;
      JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
      movesContainer.add(separator, moveConstraints);

      moveConstraints.gridwidth = 1;
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
          ImageThemed pawnImage = new ImageThemed(pawnIconString + ".png");
          pawnImage.setSize(40, 40);
          pawnIcon = new ImageIcon(pawnImage.getImage());
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

  // to load the pawn images we need to turn the pawn number into a string ( 0 ->
  // "0000.png" , 1 -> "0001.png" , "2" -> "0010.png" to "15" -> "1111.png")
  private String pawnNumberToString(int pawn) {
    String binary = Integer.toBinaryString(pawn);
    while (binary.length() < 4) {
      binary = "0" + binary;
    }
    return binary;
  }

  @Override
  public void updatedTheme() {
    movesContainer.repaint();
  }
}
