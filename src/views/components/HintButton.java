package src.views.components;

import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.components.ImageThemed;
import src.views.components.TranslatedString;
import src.views.listeners.ThemeListener;
import src.views.utils.EventsHandler;
import src.views.utils.GameStatusHandler;
import src.views.utils.ImageUtils;
import src.views.utils.ThemeUtils;

/**
 * Represents a button for giving a hint.
 */
public class HintButton extends JButton implements ThemeListener {
    private TranslatedString tooltip;
    private ImageThemed image = new ImageThemed("exit.png");
    ImageIcon icon;

    /**
     * Constructs a HintButton.
     */
    public HintButton() {
        ThemeUtils.addThemeListener(this);

        image.setSize(32, 32);

        icon = new ImageIcon(image.getImage());

        setIcon(icon);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addActionListener(e -> {
            try {
                if (EventsHandler.getController().isPlayPhase()){
                    int[] hint = EventsHandler.getController().playShotHint();
                    int row = hint[0];
                    int col = hint[1];
                    EventsHandler.showSnackbar("Hint: Consider playing at (" + row + ", " + col + ")");
                } else {
                    int hint = EventsHandler.getController().selectPawnHint();
                    EventsHandler.showSnackbar("Hint : " + hint);
                }
                } catch (Exception ex) {
                    EventsHandler.showSnackbar("Cannot provide hint: " + ex.getMessage());
            }
        });

        tooltip = new TranslatedString("redoButtonTooltip", this, true);
    }

    @Override
    public void updatedTheme() {
        ImageIcon icon = new ImageIcon(image.getImage());
        setIcon(icon);
    }
}
