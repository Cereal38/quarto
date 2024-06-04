package src.views.components;

import javax.swing.JButton;

/**
 * A button with translated text.
 */
public class TranslatedButton extends JButton {

    TranslatedString translatedString;

    /**
     * Constructs a new TranslatedButton with the specified translation key.
     *
     * @param key the translation key for the button text
     */
    public TranslatedButton(String key) {
        super();
        translatedString = new TranslatedString(key, this);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setText(translatedString.getText());
    }
}
