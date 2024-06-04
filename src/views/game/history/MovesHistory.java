package src.views.game.history;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import src.model.game.QuartoHistory;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.DimensionUtils;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ThemeUtils;

/**
 * Represents a scrollable panel displaying the moves history.
 */
public class MovesHistory extends JScrollPane implements ThemeListener {

  private JPanel movesContainer;
  private int maxDisplayedMoves = 10; // Maximum displayed moves
  int isSelectedCounter = 0;
  private TranslatedString placedItAtStr = new TranslatedString("placed-it-at");
  private TranslatedString selectedThePawnStr = new TranslatedString("selected-the-pawn");
  private ImageThemed bgImage = new ImageThemed("squared-background.png");

  /**
   * Constructs a MovesHistory panel.
   */
  public MovesHistory() {
    ThemeUtils.addThemeListener(this);

    setPreferredSize(new Dimension(200, DimensionUtils.getMainFrameHeight()));

    // Create a panel to hold the title and moves container
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Create and set up the title label
    JLabel titleLabel = new JLabel("Move History");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titleLabel.setPreferredSize(new Dimension(200, 20)); // Adjust the height as needed
    //set  background color to d3a547 and color to white
    titleLabel.setForeground(new java.awt.Color(255, 255, 255));
    titleLabel.setBackground(new java.awt.Color(211, 165, 71));
    titleLabel.setOpaque(true);
    //add margin to the title
    mainPanel.add(titleLabel, BorderLayout.NORTH);

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

    // Add movesContainer to the mainPanel
    mainPanel.add(movesContainer, BorderLayout.CENTER);

    setViewportView(mainPanel);
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    clear();
    updateHistory();
  }

  /**
   * Adds a move with the specified text and icon to the history.
   * 
   * @param move The text describing the move.
   * @param icon The icon representing the move.
   */
  public void addMove(String move, ImageIcon icon) {
    GameStatusHandler.addMove(move, icon);
    updateMovesContainer();
  }

  /**
   * Clears the moves history.
   */
  public void clear() {
    GameStatusHandler.clearMoves();
    updateMovesContainer();
  }

  /**
   * Updates the moves container with the latest moves.
   */
  private void updateMovesContainer() {
    movesContainer.removeAll();

    GridBagConstraints moveConstraints = new GridBagConstraints();
    moveConstraints.gridx = 0;
    moveConstraints.fill = GridBagConstraints.HORIZONTAL;
    moveConstraints.weightx = 1.0;

    // Add moves to container with separators in between
    for (int i = GameStatusHandler.getMoveComponents().size() - 1; i >= 0; i--) {
      moveConstraints.gridy = 2 * i; // Move
      moveConstraints.insets = new Insets(5, 70, 5, 70); // Set insets to reduce space

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
    //make sure the scroll bar invisible 
    getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

  }

  /**
   * Updates the history based on the game's state.
   */
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
  /**
   * Converts a pawn number to a binary string representation.
   *
   * @param pawn The pawn number to convert.
   * @return The binary string representation of the pawn number.
   */
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
