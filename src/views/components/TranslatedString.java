package src.views.components;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import src.views.listeners.LanguageChangeListener;
import src.views.utils.LangUtils;

/**
 * The TranslatedString class represents a string that can be translated to
 * different languages. It implements the LanguageChangeListener interface to
 * listen for language change events. It works with buttons and labels.
 */
public class TranslatedString implements LanguageChangeListener {

  private String text;
  private String key;
  private JButton button = null;
  private JLabel label = null;
  private JComponent componentWithTooltip;

  /**
   * Constructs a TranslatedString object with the specified key. The text is
   * updated based on the current language.
   * 
   * @param key the key used to retrieve the translated text
   */
  public TranslatedString(String key) {
    this.key = key;
    updateText();
    LangUtils.addLanguageChangeListener(this);
  }

  /**
   * Constructs a TranslatedString object with the specified key and button. The
   * text is updated based on the current language.
   * 
   * @param key    the key used to retrieve the translated text
   * @param button the button to update with the translated text
   */
  public TranslatedString(String key, JButton button) {
    this.key = key;
    this.button = button;
    updateText();
    LangUtils.addLanguageChangeListener(this);
  }

  /**
   * Constructs a TranslatedString object with the specified key and label. The
   * text is updated based on the current language.
   * 
   * @param key   the key used to retrieve the translated text
   * @param label the label to update with the translated text
   */
  public TranslatedString(String key, JLabel label) {
    this.key = key;
    this.label = label;
    updateText();
    LangUtils.addLanguageChangeListener(this);
  }

  public TranslatedString(String key, JComponent componentWithTooltip, boolean isTooltip) {
    this.key = key;
    this.componentWithTooltip = componentWithTooltip;
    LangUtils.addLanguageChangeListener(this);
    updateText();
  }

  /**
   * Gets the translated text.
   * 
   * @return the translated text
   */
  public String getText() {
    return text;
  }

  /**
   * Updates the text based on the current language. If a button is specified, it
   * updates the button text as well.
   */
  @Override
  public void updateText() {
    text = LangUtils.getText(key);
    if (button != null) {
      button.setText(text);
    }
    if (label != null) {
      label.setText(text);
    }
    if (componentWithTooltip != null) {
      componentWithTooltip.setToolTipText(text);
    }
  }

  /**
   * Sets the key used to retrieve the translated text.
   * 
   * @param key the key used to retrieve the translated text
   */
  public void setKey(String key) {
    this.key = key;
    updateText();
  }

  @Override
  public String toString() {
    return getText();
  }

}
