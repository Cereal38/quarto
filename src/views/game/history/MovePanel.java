package src.views.game.history;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents a panel displaying a move with an icon.
 */
public class MovePanel extends JPanel {
    /**
     * Constructs a MovePanel with the specified text and icon.
     * @param text The text to display.
     * @param icon The icon to display.
     */
    public MovePanel(String text, ImageIcon icon) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 50)); // Fixed size
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setForeground(Color.WHITE); // Adjust text color if necessary

        JLabel iconLabel = new JLabel(icon);

        add(textLabel, BorderLayout.CENTER);
        add(iconLabel, BorderLayout.EAST);

        setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding
        setOpaque(false); // Make the panel background transparent
    }
}
