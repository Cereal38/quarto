package src.views.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import src.views.players.names.ChoosePlayers; // Import ChoosePlayers class (assuming it's in the same package)

// TextFieldChangeListener now implements DocumentListener
public class TextFieldChangeListener implements DocumentListener {

    private ChoosePlayers choosePlayers; // Reference to ChoosePlayers instance

    public TextFieldChangeListener(ChoosePlayers choosePlayers) {
        this.choosePlayers = choosePlayers;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        choosePlayers.updateStartButtonVisibility(); // Call method from ChoosePlayers instance
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        choosePlayers.updateStartButtonVisibility(); // Call method from ChoosePlayers instance
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // Not used for plain text components like JTextField
    }
}
