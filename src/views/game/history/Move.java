package src.views.game.history;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Move extends JLabel {
    public Move(String text) {
        super(text);
        setPreferredSize(new Dimension(180, 50)); // Fixed size
        setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding
        // set a black border to the label
        setForeground(Color.BLACK);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public void updateMoveText(String newText) {
        setText(newText);
    }
}
