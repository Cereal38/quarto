package src.views.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import src.views.utils.LangUtils;

public class LanguageButton extends JButton {
  private static final int LANG_EN = 0;
  private static final int LANG_FR = 1;
  private int lang = LANG_EN;
  private boolean isLightTheme = true;
  private TranslatedString tooltip;
  private ImageThemed enImage = new ImageThemed("en.png");
  private ImageThemed frImage = new ImageThemed("fr.png");

  public LanguageButton() {
    // Set the default icon based on the current theme
    updateIcon(isLightTheme);

    // Add style
    setBorder(BorderFactory.createEmptyBorder());
    setContentAreaFilled(false);
    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Add action to switch languages
    addActionListener(e -> switchLanguage());

    // Add tooltip
    tooltip = new TranslatedString("languageButtonTooltip", this, true);
  }

  private void switchLanguage() {
    LangUtils.setLang(lang == LANG_EN ? LANG_FR : LANG_EN);
    lang = (lang + 1) % 2;
    updateIcon(isLightTheme);
  }

  public void updateIcon(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;

    if (lang == LANG_EN) {
      frImage.setSize(32, 32);
      ImageIcon icon = new ImageIcon(frImage.getImage());
      setIcon(icon);
    } else {
      enImage.setSize(32, 32);
      ImageIcon icon = new ImageIcon(enImage.getImage());
      setIcon(icon);
    }
  }

  public void changeTheme(boolean isLightTheme) {
    this.isLightTheme = isLightTheme;
    updateIcon(isLightTheme);
  }
}
