package src.views.components;

import javax.swing.JLabel;

public class TranslatedLabel extends JLabel {

  TranslatedString translatedString;

  public TranslatedLabel(String key) {
    super();
    translatedString = new TranslatedString(key, this);
    setText(translatedString.getText());
  }

  public void setKey(String key) {
    translatedString.setKey(key);
    setText(translatedString.getText());
  }
}
