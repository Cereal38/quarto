package src.views.components;

import javax.swing.JButton;
import src.views.listeners.LanguageChangeListener;
import src.views.utils.LangUtils;

public class TranslatedString implements LanguageChangeListener {

  private String text;
  private String key;
  private JButton button = null;

  public TranslatedString(String key) {
    this.key = key;
    updateText();
    LangUtils.addLanguageChangeListener(this);
  }

  public TranslatedString(String key, JButton button) {
    this.key = key;
    this.button = button;
    updateText();
    LangUtils.addLanguageChangeListener(this);
  }

  public String getText() {
    return text;
  }

  @Override
  public void updateText() {
    System.out.println("Updating text for key: " + LangUtils.getText(key));
    text = LangUtils.getText(key);
    if (button != null) {
      button.setText(text);
    }
  }

}
