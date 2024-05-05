package src.views.components;

import javax.swing.JButton;

public class TranslatedButton extends JButton {

    TranslatedString translatedString;

    public TranslatedButton(String key) {
        super();
        translatedString = new TranslatedString(key, this);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setText(translatedString.getText());
    }
}
