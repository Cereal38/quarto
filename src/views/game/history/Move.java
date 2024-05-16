package src.views.game.history;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Move extends JPanel {
    private JLabel moveLbl = new JLabel();

    public Move(String move) {
        setLayout(new GridBagLayout());
        moveLbl.setText(move);
        add(moveLbl);
    }
}