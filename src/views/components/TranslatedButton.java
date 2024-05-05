package src.views.components;

import javax.swing.JButton;

public class TranslatedButton extends JButton {

  TranslatedString translatedString;

  public TranslatedButton(String key) {
    super();
    translatedString = new TranslatedString(key, this);
    setText(translatedString.getText());
  }
}
