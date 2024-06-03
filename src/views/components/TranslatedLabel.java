package src.views.components;

import javax.swing.JLabel;

/**
 * A label with translated text.
 */
public class TranslatedLabel extends JLabel {

  TranslatedString translatedString;

  /**
   * Constructs a new TranslatedLabel with the specified translation key.
   *
   * @param key the translation key for the label text
   */
  public TranslatedLabel(String key) {
    super();
    translatedString = new TranslatedString(key, this);
    setText(translatedString.getText());
  }

  /**
   * Sets the translation key for the label text and updates the text accordingly.
   *
   * @param key the translation key for the label text
   */
  public void setKey(String key) {
    translatedString.setKey(key);
    setText(translatedString.getText());
  }
}
