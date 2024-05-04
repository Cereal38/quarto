package src.views.components;

import javax.swing.JLabel;
import src.views.listeners.LanguageChangeListener;
import src.views.utils.LangUtils;

public class TextLabel extends JLabel implements LanguageChangeListener {

  private String key;

  public TextLabel(String key) {
    super(LangUtils.getText(key));
    this.key = key;
  }

  @Override
  public void updateText() {
    setText(LangUtils.getText(key));
  }
}
