package src.views.game.history;

import javax.swing.JLabel;

public class Move extends JLabel {
    public Move(String text) {
        super(text);
    }

    public void updateMoveText(String newText) {
        setText(newText);
    }
}
