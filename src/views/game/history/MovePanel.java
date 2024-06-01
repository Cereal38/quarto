package src.views.game.history;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MovePanel extends JPanel {
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
