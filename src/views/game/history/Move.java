package src.views.game.history;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Represents a move in the game history.
 */
public class Move extends JLabel {
    /**
     * Constructs a Move label with the specified text.
     * @param text The text of the move.
     */
    public Move(String text) {
        super(text);
        setPreferredSize(new Dimension(180, 50)); // Fixed size
        setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding
        setForeground(Color.BLACK); // Set text color to black
        setOpaque(false); // Make the label background transparent
        setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
    }

    /**
     * Updates the text of the move label.
     * @param newText The new text of the move.
     */
    public void updateMoveText(String newText) {
        setText(newText);
    }
}
